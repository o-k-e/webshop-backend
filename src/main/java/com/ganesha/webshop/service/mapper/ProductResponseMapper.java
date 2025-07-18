package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResponseMapper {

    private final CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper;
    private final ProductImageResponseMapper productImageResponseMapper;

    @Autowired
    public ProductResponseMapper(CategoryWithIdAndNameResponseMapper categoryWithIdAndNameResponseMapper, ProductImageResponseMapper productImageResponseMapper) {
        this.categoryWithIdAndNameResponseMapper = categoryWithIdAndNameResponseMapper;
        this.productImageResponseMapper = productImageResponseMapper;
    }

    public ProductResponse mapToProductResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                categoryWithIdAndNameResponseMapper.mapToCategoryResponseListWithIdAndName(product.getCategories()),
                productImageResponseMapper.mapToListOfProductImageResponse(product)
                );
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
