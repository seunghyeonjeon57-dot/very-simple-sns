package com.seunghyeon.verysimplesns.controller;


import com.seunghyeon.verysimplesns.dto.response.ImageResponse;
import com.seunghyeon.verysimplesns.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService service;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> upload(
            @RequestParam MultipartFile file
            ){

        String image = service.store(file);
        ImageResponse response  = new ImageResponse(image);
        return ResponseEntity.ok(response);
    }
}
