package com.ganesha.webshop.model.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found by id: " + id);
    }
}
