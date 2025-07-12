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

    @PostConstruct //miutan letrejon a Bean, ezt lefuttatja
    public void uploadDirInitializer() { // megnezi hogy letezik e a folder, ha nem, letrehozza
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String upload(String filename, String base64Image) { // frontendrol file kivalsztos imputbol csinal majd egy base64 stringet, belefuzi egy json-ba es elkuldi a controllernek
        try {                                               //base64Image: src="data:image/png;base64, iVBORw0kgoAAADgdy34akdoKLoepaoe...ez meg hosszan...="
            String[] parts = base64Image.split(",");
            String base64Data = (parts.length > 1) ? parts[1] : parts[0];
            byte[] imageBytes = Base64.getDecoder().decode(base64Data); // string-et binaris adatta alakitja
            String fileName = UUID.randomUUID() + "-" + filename; // elkerulni h 2 ugyanolyan nevu file feltoltodjon, ezert UUID lesz az image neve elmentve a serveren?
            File file = new File(uploadDir, fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(imageBytes);  // file-ba binaris adatokat tud irni
            }

            return fileName;
        } catch (IOException e) {
            return "";
        }
    }
}
