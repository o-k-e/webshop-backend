package com.ganesha.webshop.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateProductRequest(
        @NotBlank
        String productName,

        @NotBlank
        String productDescription,

        @Positive
        double price,

        @Size(min = 1, message = "At least one category must be provided.")
        List<@NotNull Long> categoryIds,

        @Size(min = 1, message = "At least one image must be provided.")
        List<@NotBlank String> imageFileNames
) {}
