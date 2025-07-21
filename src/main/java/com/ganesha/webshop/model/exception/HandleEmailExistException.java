package com.ganesha.webshop.model.exception;

public class HandleEmailExistException extends RuntimeException {
    public HandleEmailExistException() {
        super("Email already exists");
    }
}
