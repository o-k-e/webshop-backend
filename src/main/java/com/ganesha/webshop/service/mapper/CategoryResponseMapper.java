package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.CategoryWithIdAndNameResponse;
import com.ganesha.webshop.model.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResponseMapper {

    public CategoryWithIdAndNameResponse mapToCategoryResponseWithIdAndName(Category category) {

        return new CategoryWithIdAndNameResponse(
                category.getId(),
                category.getCategoryName()
        );
    }

    public List<CategoryWithIdAndNameResponse> mapToCategoryResponseListWithIdAndName(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryResponseWithIdAndName)
                .toList();
    }
}
