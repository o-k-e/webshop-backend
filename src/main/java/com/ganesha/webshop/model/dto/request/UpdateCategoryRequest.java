package com.ganesha.webshop.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(@NotBlank(message = "Category name is required") String name) {
}
