package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.request.NewCategoryRequest;
import com.ganesha.webshop.model.dto.response.CategoryIdResponse;
import com.ganesha.webshop.model.dto.response.CategoryResponse;
import com.ganesha.webshop.model.dto.response.CategoryWithIdAndNameResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.model.exception.HandleCategoryExistException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.service.mapper.CategoryIdResponseMapper;
import com.ganesha.webshop.service.mapper.CategoryWithIdAndNameResponseMapper;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper;
    private ProductResponseMapper productResponseMapper;
    private CategoryIdResponseMapper categoryIdResponseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper, ProductResponseMapper productResponseMapper, CategoryIdResponseMapper categoryIdResponseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryWithIdAndNameResponseMapper = categoryWithIdAndNameResponseMapper;
        this.productResponseMapper = productResponseMapper;
        this.categoryIdResponseMapper = categoryIdResponseMapper;
    }

    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        List<Product> products = category.getProducts();
        return productResponseMapper.mapToProductResponseList(products);
    }

    public List<CategoryWithIdAndNameResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseListWithIdAndName(categories);
    }

    public CategoryIdResponse create(NewCategoryRequest newCategoryRequest) {
        Optional<Category> category = categoryRepository.findByCategoryName(newCategoryRequest.categoryName());
        if (category.isPresent()) {
            throw new HandleCategoryExistException(newCategoryRequest.categoryName());
        }
        Category newCategory = categoryIdResponseMapper.mapToEntity(newCategoryRequest);
        categoryRepository.save(newCategory);
        return new CategoryIdResponse(newCategory.getId());
    }

//    public CategoryWithIdAndNameResponse findById(Long id) {
//        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
//        return categoryWithIdAndNameResponseMapper.mapToCategoryResponseWithIdAndName(category);
//    }
}
