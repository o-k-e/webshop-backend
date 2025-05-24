package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.CategoryResponse;
import com.ganesha.webshop.model.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResponseMapper {

    private ProductResponseMapper productResponseMapper;

    public CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCategoryName(),
                productResponseMapper.mapToProductResponseList(category.getProducts())
        );
    }

    public List<CategoryResponse> mapToResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::mapToResponse)
                .toList();
    }
}
