package com.seunghyeon.verysimplesns.service;

import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String dir;

    private final static Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif");
    @PostConstruct
    public void init() throws IOException {
        this.dir= Files.createDirectories(Path.of(dir)).toString();
    }


    public String store(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if(originalFilename ==null ){
            throw new SimpleSnsException("파일을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if(extension==null){
            throw new SimpleSnsException("확장자가 없는 파일입니다.",HttpStatus.BAD_REQUEST);
        }
        String fileExt = extension.toLowerCase();
        if(!ALLOWED_EXTENSIONS.contains(fileExt)){
            throw new SimpleSnsException("사용할 수 없는 확장자 입니다.",HttpStatus.BAD_REQUEST);
        }

        String fileName = UUID.randomUUID().toString() + "." + fileExt;
        Path path = Path.of(dir);
        Path finalPath = path.resolve(fileName);
        try{file.transferTo(finalPath);}catch (IOException e){
            throw new SimpleSnsException("파일 저장에 실패했습니다",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "/images/"+fileName;
    }
}
