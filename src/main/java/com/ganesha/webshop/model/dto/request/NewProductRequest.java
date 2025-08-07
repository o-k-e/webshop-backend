package com.ganesha.webshop.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record NewProductRequest(

        @NotBlank(message = "Product name is required.")
        String productName,

        @NotBlank(message = "Description must not be empty.")
        String description,

        @Positive(message = "Price must be a positive number.")
        double price,

        @NotEmpty(message = "At least one category must be selected.")
        List<@NotNull Long> categoryIds,

        @NotEmpty(message = "At least one image must be provided.")
        List<@NotBlank String> imageFileNames) {
}
