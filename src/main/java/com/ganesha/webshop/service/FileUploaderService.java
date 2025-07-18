package com.ganesha.webshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class responsible for handling image file uploads to a local directory.
 * <p>
 * It supports Base64 image decoding and ensures that the target upload folder exists.
 * The image filenames are stored with a unique UUID prefix to avoid collisions.
 */
@Service
public class FileUploaderService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploaderService.class);
    /**
     * The directory where image files will be saved.
     * <p>
     * This path is injected from the application properties using the key:
     * <code>fileuploader.directory</code>.
     * Example:
     * <pre>
     * fileuploader.directory=uploads/images
     * </pre>
     */
    @Value("${fileuploader.directory}")
    private String uploadDir;

    /**
     * Initializes the upload directory if it doesn't exist.
     * <p>
     * This method is called automatically after the Spring bean is constructed.
     * It creates the directory structure defined by {@code uploadDir} if it is missing.
     */
    @PostConstruct //miutan letrejon a Bean, ezt lefuttatja
    public void uploadDirInitializer() { // megnezi hogy letezik e a folder, ha nem, letrehozza
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Saves a Base64-encoded image file to the upload directory.
     * <p>
     * The image will be saved using a generated unique filename that includes a UUID to avoid filename collisions.
     * The expected Base64 format from the frontend looks like:
     * <pre>
     {
     *   "filename": "xy.jpg",
     *   "base64": "data:image/png;base64,iVBORw0KGgoAAAANS..."
     * }
     * </pre>
     *
     * @param filename     the original filename sent from the frontend (used as suffix)
     * @param base64Image  the Base64-encoded image string
     * @return the saved filename (including the UUID prefix), or an empty string if the upload failed
     */
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

            logger.info("{} has been uploaded", fileName);
            return fileName;
        } catch (IOException e) {
            return "";
        }
    }
}
