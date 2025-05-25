package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.CategoryResponseWithIdAndName;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
//import com.ganesha.webshop.service.mapper.CategoryMapper;
import com.ganesha.webshop.service.mapper.CategoryResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryResponseMapper categoryResponseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryResponseMapper categoryResponseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryResponseMapper = categoryResponseMapper;
    }

    public List<CategoryResponseWithIdAndName> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryResponseMapper.mapToCategoryResponseListWithIdAndName(categories);
    }

    public CategoryResponseWithIdAndName findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryResponseMapper.mapToCategoryResponseWithIdAndName(category);
    }
}
