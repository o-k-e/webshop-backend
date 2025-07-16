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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final NewProductMapper newProductMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper, CategoryRepository categoryRepository, NewProductMapper newProductMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.categoryRepository = categoryRepository;
        this.newProductMapper = newProductMapper;
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

//        List<Long> categoryIds = Optional.ofNullable(updateProductRequest.categoryIds())
//                .orElseThrow(() -> new IllegalArgumentException("categoryIds must not be null"));

        List<Category> categories = updateProductRequest.categoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                .collect(Collectors.toList());
        productToUpdate.setCategories(categories);

        List<ProductImage> newImages = updateProductRequest.imageFileNames().stream()
                .map((fileName -> {
                    ProductImage image = new ProductImage();
                    image.setUrl(fileName);
                    image.setProduct(productToUpdate);
                    return image;
                })).collect(Collectors.toList());

        productToUpdate.getImages().clear();
        productToUpdate.getImages().addAll(newImages);

        productRepository.save(productToUpdate);
        return productResponseMapper.mapToProductResponse(productToUpdate);
    }
}
