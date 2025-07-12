package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.dto.response.ProductIdResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.exception.ProductNotFoundException;
import com.ganesha.webshop.repository.ProductRepository;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
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
        Product newProduct = new Product();
        newProduct.setProductName(newProductRequest.productName());
        newProduct.setProductDescription(newProductRequest.description());
        newProduct.setPrice(newProductRequest.price());
        Category newCategory = new Category();
        newCategory.setId(newProductRequest.categoryId());
        newProduct.setCategories(List.of(newCategory));
        ProductImage newProductImage = new ProductImage();
        newProductImage.setUrl(newProductRequest.imageFileName());
        newProduct.setImages(List.of(newProductImage));
        newProductImage.setProduct(newProduct);

        productRepository.save(newProduct);

        return new ProductIdResponse(newProduct.getId());
    }
}
