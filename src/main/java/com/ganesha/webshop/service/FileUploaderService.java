package com.ganesha.webshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileUploaderService {

    @Value("${fileuploader.directory}")
    private String uploadDir;

    @PostConstruct
    public void uploadDirInitializer() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String upload(String base64Image) {
        try {
            String[] parts = base64Image.split(",");
            String base64Data = (parts.length > 1) ? parts[1] : parts[0];
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            String fileName = UUID.randomUUID() + ".jpg";
            File file = new File(uploadDir, fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(decodedBytes);
            }

            return fileName;
        } catch (IOException e) {
            return "";
        }
    }
}
