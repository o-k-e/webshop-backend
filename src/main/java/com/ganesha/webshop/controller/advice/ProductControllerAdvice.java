package com.ganesha.webshop.controller.advice;

import com.ganesha.webshop.model.dto.response.ErrorResponse;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.model.exception.ImageFileNotFoundException;
import com.ganesha.webshop.model.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse productNotFoundHandler(ProductNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse categoryNotFoundHandler(CategoryNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ImageFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse imageFileNotFoundHandler(ImageFileNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
