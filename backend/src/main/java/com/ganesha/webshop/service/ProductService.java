package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Product;
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
}
