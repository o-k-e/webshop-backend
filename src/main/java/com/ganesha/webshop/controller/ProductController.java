package com.ganesha.webshop.controller;

import com.ganesha.webshop.model.dto.request.ProductRequest;
import com.ganesha.webshop.model.dto.response.ProductResponseWithFilteredCategories;
import com.ganesha.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseWithFilteredCategories> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseWithFilteredCategories getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

//    @PostMapping("/create")
//    public ProductResponseWithFilteredCategories createProduct(@RequestBody ProductRequest productRequest) {
//
//    }
}
