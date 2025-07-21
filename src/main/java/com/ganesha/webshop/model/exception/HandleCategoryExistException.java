package com.ganesha.webshop.model.exception;

public class HandleCategoryExistException extends RuntimeException {
    public HandleCategoryExistException(String name) {
        super("Category already exists: " + name);
    }
}
