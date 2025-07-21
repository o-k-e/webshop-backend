package com.ganesha.webshop.controller;

import com.ganesha.webshop.model.dto.response.CategoryResponse;
import com.ganesha.webshop.model.dto.response.CategoryWithIdAndNameResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}/products")
    List<ProductResponse> getProductsByCategory(@PathVariable Long id) {
        return categoryService.getProductsByCategoryId(id);
    }

    @GetMapping
    List<CategoryWithIdAndNameResponse> getCategories() {
        return categoryService.findAll();
    }
}
