package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.CategoryResponseWithIdAndName;
import com.ganesha.webshop.model.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResponseMapper {

    public CategoryResponseWithIdAndName mapToCategoryResponseWithIdAndName(Category category) {

        return new CategoryResponseWithIdAndName(
                category.getId(),
                category.getCategoryName()
        );
    }

    public List<CategoryResponseWithIdAndName> mapToCategoryResponseListWithIdAndName(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryResponseWithIdAndName)
                .toList();
    }
}
