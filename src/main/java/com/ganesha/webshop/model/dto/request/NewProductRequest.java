package com.ganesha.webshop.model.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record NewProductRequest(

        @NotBlank(message = "Product name is required.")
        String productName,

        @NotBlank(message = "Description must not be empty.")
        String description,

        @Positive(message = "Price must be a positive number.")
        double price,

        @PositiveOrZero(message = "Quantity must be 0 or more.")
        int quantity,

        @NotEmpty(message = "At least one category must be selected.")
        List<@NotNull Long> categoryIds,

        @NotEmpty(message = "At least one image must be provided.")
        List<@NotBlank String> imageFileNames) {
}
