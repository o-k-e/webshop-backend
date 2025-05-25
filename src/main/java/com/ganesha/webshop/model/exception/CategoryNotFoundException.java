package com.ganesha.webshop.model.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category not found by id: " + id);
    }
}
