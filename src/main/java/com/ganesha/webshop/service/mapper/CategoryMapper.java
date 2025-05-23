package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.CategoryResponse;
import com.ganesha.webshop.model.entity.products.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category mapToCategory(CategoryResponse categoryResponse) {
        Category category = new Category();
        category.setId(categoryResponse.id());
        category.setCategoryName(categoryResponse.categoryName());

        return category;
    }
}
