package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.SuccessResponse;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.exception.ProductImageNotFoundException;
import com.ganesha.webshop.repository.ImageRepository;
import com.ganesha.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * The directory path where uploaded image files are stored.
 * <p>
 * This value is injected from the application properties using the key
 * <code>fileuploader.directory</code>.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final FileDeletionService fileDeletionService;

    @Value("${fileuploader.directory}")
    private String uploadDir;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, FileDeletionService fileDeletionService) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.fileDeletionService = fileDeletionService;
    }

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

    public SuccessResponse deleteImageById(Long imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ProductImageNotFoundException(imageId));

        Product product = image.getProduct();
        if (product != null) {
            product.getImages().remove(image);
            productRepository.save(product);
        }

        fileDeletionService.deleteFile(List.of(image.getUrl()));
        imageRepository.delete(image);

        return new SuccessResponse(true);
    }
}