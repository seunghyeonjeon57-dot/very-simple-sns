package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 이미지를 로컬 디스크에 저장하고, 정적 리소스 핸들러(/images/**)로 서빙 가능한
 * 상대 URL을 돌려주는 아주 단순한 파일 저장소.
 *
 * 개인 사이드 프로젝트 + 단일 인스턴스 배포라는 규모를 감안해 S3 같은 외부 스토리지 대신
 * 로컬 디스크로 구현함 (YAGNI) - 다만 인스턴스를 여러 대로 늘리면 이 방식은 그대로 못 쓴다는 한계가 있음.
 */
@Slf4j
@Service
public class FileStorageService {

    private final Path uploadDirPath;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(uploadDirPath);
        } catch (IOException e) {
            throw new IllegalStateException("이미지 업로드 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new SimpleSnsException("업로드할 파일이 비어있습니다.", HttpStatus.BAD_REQUEST);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String storedFilename = UUID.randomUUID() + extension;

        try {
            Path target = uploadDirPath.resolve(storedFilename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("이미지 저장 실패", e);
            throw new SimpleSnsException("이미지 저장 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "/images/" + storedFilename;
    }
}
