package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.response.CategoryResponse;
import com.ganesha.webshop.model.dto.response.CategoryWithIdAndNameResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.service.mapper.CategoryWithIdAndNameResponseMapper;
import com.ganesha.webshop.service.mapper.ProductImageResponseMapper;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper;
    private ProductResponseMapper productResponseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper, ProductResponseMapper productResponseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryWithIdAndNameResponseMapper = categoryWithIdAndNameResponseMapper;
        this.productResponseMapper = productResponseMapper;
    }
//    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
//        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
//        List<Product> products = category.getProducts();
//        return productResponseMapper.mapToProductResponseList(products);
//    }

//    public List<CategoryResponse> findAll() {
//        List<Category> categories = categoryRepository.findAll();
//        return
//    }

//    public List<CategoryWithIdAndNameResponse> findAll() {
//        List<Category> categories = categoryRepository.findAll();
//        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseListWithIdAndName(categories);
//    }

//    public CategoryWithIdAndNameResponse findById(Long id) {
//        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
//        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseWithIdAndName(category);
//    }
}
