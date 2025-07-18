package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.CategoryWithIdAndNameResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.service.mapper.CategoryWithIdAndNameResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryWithIdAndNameResponseMapper = categoryWithIdAndNameResponseMapper;
    }

    public List<CategoryWithIdAndNameResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseListWithIdAndName(categories);
    }

    public CategoryWithIdAndNameResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseWithIdAndName(category);
    }
}
