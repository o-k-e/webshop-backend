package com.ganesha.webshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Optional;

@Service
public class ImageService {

  @Value("${fileuploader.directory}")
  private String uploadDir;

    public Optional<File> serveFile(String fileName) { //kiolvassa ha az image file benne van a folderben
        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(file);
    }
}