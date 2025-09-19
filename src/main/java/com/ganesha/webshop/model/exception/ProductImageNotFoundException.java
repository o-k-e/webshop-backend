package com.ganesha.webshop.model.exception;

public class ProductImageNotFoundException extends RuntimeException {
    public ProductImageNotFoundException(Long id) {
        super("Could not find image with id: " + id);
    }
}
