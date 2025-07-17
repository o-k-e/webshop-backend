package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.dto.request.UpdateProductRequest;
import com.ganesha.webshop.model.dto.response.ProductIdResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.model.exception.ImageFileNotFoundException;
import com.ganesha.webshop.model.exception.ProductNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.repository.ProductRepository;
import com.ganesha.webshop.service.mapper.NewProductMapper;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
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

    @Transactional
    public ProductIdResponse create(NewProductRequest newProductRequest) {
        Category category = categoryRepository.findById(newProductRequest.categoryId()).orElseThrow(() -> new CategoryNotFoundException(newProductRequest.categoryId()));
        Product product = newProductMapper.mapToEntity(newProductRequest, category);
        productRepository.save(product);
        return new ProductIdResponse(product.getId());
    }

    @Transactional
    public ProductResponse update(long id, UpdateProductRequest updateProductRequest) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        productToUpdate.setProductName(updateProductRequest.productName());
        productToUpdate.setProductDescription(updateProductRequest.productDescription());
        productToUpdate.setPrice(updateProductRequest.price());

        List<Category> categories = findCategories(updateProductRequest);
        productToUpdate.setCategories(categories);

        List<String> existingFileNames = getExistingFileNames(productToUpdate);
        List<ProductImage> newImages = extractValidNewImages(updateProductRequest, existingFileNames, productToUpdate);
        productToUpdate.getImages().addAll(newImages);

        productRepository.save(productToUpdate);

        return productResponseMapper.mapToProductResponse(productToUpdate);
    }

    public List<Category> findCategories(UpdateProductRequest updateProductRequest) {

        return updateProductRequest.categoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)))
                .collect(Collectors.toList());
    }

    public List<String> getExistingFileNames(Product productToUpdate) {
        List<ProductImage> existingImages = productToUpdate.getImages();

        return existingImages.stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }

    public List<ProductImage> extractValidNewImages(UpdateProductRequest updateProductRequest, List<String> existingFileNames, Product productToUpdate) {

           return updateProductRequest.imageFileNames().stream()
                   .filter(existingFileName -> !existingFileNames.contains(existingFileName))
                   .map(fileName  -> {
                       Optional<File> fileOptional = imageService.serveFile(fileName);
                       if (fileOptional.isEmpty()) {
                           // megtortenhet hogy nem mentette el? Frontend ellenorzi ezt majd? de backenden mindenkepp kell ellenoriznie? ha igen, akkor az egesz folyamatot ujra kellene inditani a frontendrol? Transactional?
                           throw new ImageFileNotFoundException(fileName);
                       }
                       ProductImage newImage = new ProductImage();
                       newImage.setUrl(fileName);
                       newImage.setProduct(productToUpdate); //mashoz tartozo kepet hozza tud adni ujra uj id-val
                       return newImage;
                   })
                   .collect(Collectors.toList());
    }
}
