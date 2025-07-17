package com.ganesha.webshop.model.exception;

public class ImageFileNotFoundException extends RuntimeException {
    public ImageFileNotFoundException(String fileName) {
        super("The image file could not be found on the server. Please upload the image before saving the product. File name: " + fileName);
    }
}
