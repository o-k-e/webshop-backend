package com.ganesha.webshop.model.dto.request;

public record NewProductRequest(String productName, String description, double price, Long categoryId, String imageFileName) {
}
