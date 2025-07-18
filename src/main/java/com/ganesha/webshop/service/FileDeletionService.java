package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for deleting image files from the upload directory.
 * <p>
 * It receives a list of filenames and attempts to delete each corresponding file
 * from the configured upload folder. It returns a {@link SuccessResponse}
 * indicating whether all deletions were successful.
 */
@Service
public class FileDeletionService {

    private static final Logger logger = LoggerFactory.getLogger(FileDeletionService.class);
    /**
     * The base directory path where image files are stored.
     * <p>
     * Injected from application properties using the key:
     * <code>fileuploader.directory</code>.
     * Example:
     * <pre>
     * fileuploader.directory=uploads/images/
     * </pre>
     */
    @Value("${fileuploader.directory}")
    private String directoryPath;

    /**
     * Deletes a list of files from the upload directory.
     * <p>
     * For each filename, the method attempts to locate and delete the corresponding file.
     * Logging is performed at the appropriate level:
     * <ul>
     *     <li>{@code INFO} – if the file was successfully deleted</li>
     *     <li>{@code WARN} – if the file does not exist or could not be deleted</li>
     * </ul>
     * <p>
     * The method returns a {@link SuccessResponse} indicating whether all deletions were successful.
     *
     * @param fileNames a list of filenames (e.g. {@code ["image1.jpg", "abc.png"]}) to delete
     * @return a {@link SuccessResponse} with {@code success=true} if all files were deleted, or {@code false} otherwise
     */
    public SuccessResponse deleteFile(List<String> fileNames) {
        boolean allDeleted = true;

        for (String fileName : fileNames) {
            File file = new File(directoryPath, fileName);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    logger.info("{} deleted successfully", fileName);
                } else {
                    allDeleted = false;
                    logger.warn("{} could not be deleted", fileName);
                }
            } else {
                logger.warn("{} does not exist, skipping deletion", fileName);
            }
        }

        return new SuccessResponse(allDeleted);
    }
}
