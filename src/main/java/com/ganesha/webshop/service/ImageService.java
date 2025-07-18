package com.ganesha.webshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Optional;

/**
 * The directory path where uploaded image files are stored.
 * <p>
 * This value is injected from the application properties using the key
 * <code>fileuploader.directory</code>.
 */
@Service
public class ImageService {

  @Value("${fileuploader.directory}")
  private String uploadDir;

    /**
     * Retrieves an image file from the upload directory if it exists.
     * <p>
     * This method constructs the full file path based on the configured upload directory
     * and the provided file name. If the file exists, it returns an {@link Optional}
     * containing the {@link File} object; otherwise, it returns {@link Optional#empty()}.
     *
     * @param fileName the name of the image file to retrieve
     * @return an {@code Optional} containing the image file if it exists, or
     *         {@code Optional.empty()} if the file is not found
     */
    public Optional<File> getImageFileIfExists(String fileName) { //kiolvassa ha az image file benne van a folderben
        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(file);
    }
}