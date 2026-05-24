package com.example.toyproject.Controller;

import com.example.toyproject.DTO.response.ApiResponse;
import com.example.toyproject.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = fileService.saveFile(file);
        // 저장된 이미지 URL을 반환 (이 URL을 다시 spending 등록 API에 사용하면 됨)
        return ResponseEntity.ok(ApiResponse.ok(Map.of("imageUrl", imageUrl)));
    }
}
