package com.ganesha.webshop.model.exception;

public class HandleUserNameExistException extends RuntimeException {
    public HandleUserNameExistException() {
        super("User name already exists");
    }
}
