package com.ganesha.webshop.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewProductRequest(

        @NotBlank(message = "Product name is required.")
        String productName,

        @NotBlank(message = "Description must not be empty.")
        String description,

        @Positive(message = "Price must be a positive number.")
        double price,

        @NotNull(message = "Category is required.")
        Long categoryId,

        @NotBlank(message = "At least one image must be provided.")
        String imageFileName) {
}
