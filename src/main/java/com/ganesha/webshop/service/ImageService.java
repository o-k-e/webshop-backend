package com.ganesha.webshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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