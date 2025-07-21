package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.request.NewCategoryRequest;
import com.ganesha.webshop.model.entity.product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdResponseMapper {

    public Category mapToEntity(NewCategoryRequest newCategoryRequest) {
        Category category = new Category();
        category.setCategoryName(newCategoryRequest.categoryName());
        return category;
    }
}
