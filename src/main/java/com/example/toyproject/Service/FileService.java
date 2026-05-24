package com.example.toyproject.Service;

import com.example.toyproject.Exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    // 이미지가 저장될 로컬 경로 (프로젝트 루트의 uploads 폴더)
    private final String uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toString();

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomException("FILE_ERROR", "파일이 비어있습니다.");
        }

        try {
            // 폴더가 없으면 생성
            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 파일명 중복 방지를 위해 UUID 사용
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String savedFilename = UUID.randomUUID().toString() + extension;

            // 파일 저장
            Path targetPath = Paths.get(uploadPath, savedFilename);
            Files.copy(file.getInputStream(), targetPath);

            // 클라이언트가 접근할 수 있는 상대 경로 반환 (예: /uploads/abc-123.jpg)
            return "/uploads/" + savedFilename;

        } catch (IOException e) {
            throw new CustomException("FILE_ERROR", "파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
