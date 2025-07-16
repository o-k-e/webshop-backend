package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.dto.request.UpdateProductRequest;
import com.ganesha.webshop.model.dto.response.ProductIdResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.model.exception.ProductNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.repository.ProductRepository;
import com.ganesha.webshop.service.mapper.NewProductMapper;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final NewProductMapper newProductMapper;
    private final ImageService imageService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper, CategoryRepository categoryRepository, NewProductMapper newProductMapper, ImageService imageService) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.categoryRepository = categoryRepository;
        this.newProductMapper = newProductMapper;
        this.imageService = imageService;
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return productResponseMapper.mapToProductResponseList(products);
    }

    public ProductResponse findById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productResponseMapper.mapToProductResponse(product);
    }

    public ProductIdResponse create(NewProductRequest newProductRequest) {
        Category category = categoryRepository.findById(newProductRequest.categoryId()).orElseThrow(() -> new CategoryNotFoundException(newProductRequest.categoryId()));
        Product product = newProductMapper.mapToEntity(newProductRequest, category);
        productRepository.save(product);
        return new ProductIdResponse(product.getId());
    }

    public ProductResponse update(long id, UpdateProductRequest updateProductRequest) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productToUpdate.setProductName(updateProductRequest.productName());
        productToUpdate.setProductDescription(updateProductRequest.productDescription());
        productToUpdate.setPrice(updateProductRequest.price());

        List<Category> categories = updateProductRequest.categoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                .collect(Collectors.toList());

        productToUpdate.setCategories(categories);

        List<ProductImage> existingImages = productToUpdate.getImages();
        List<String> existingFileNames = existingImages.stream()
                .map(image -> image.getUrl())
                .collect(Collectors.toList());

        List<ProductImage> newImages = updateProductRequest.imageFileNames().stream()
                .filter(imageName -> !existingFileNames.contains(imageName))
                .map((fileName -> {
                    Optional<File> fileOptional = imageService.serveFile(fileName);
                    if (fileOptional.isEmpty()) {
                        // megtortenhet hogy nem mentette el? ha igen, akkor az egesz folyamatot ujra kellene inditani a frontendrol? Transactional?
                        return null;
                    }
                    ProductImage newImage = new ProductImage();
                    newImage.setUrl(fileName);
                    newImage.setProduct(productToUpdate); //mashoz tartozo kepet hozza tud adni ujra uj id-val
                    return newImage;
                }))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        productToUpdate.getImages().addAll(newImages);

        productRepository.save(productToUpdate);
        return productResponseMapper.mapToProductResponse(productToUpdate);
    }
}
