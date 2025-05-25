package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.ProductImageResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResponseMapper {

    private final CategoryResponseMapper categoryResponseMapper;

    @Autowired
    public ProductResponseMapper(CategoryResponseMapper categoryResponseMapper) {
        this.categoryResponseMapper = categoryResponseMapper;
    }

    public ProductResponse mapToProductResponse(Product product) {

        List<ProductImageResponse> imageResponses = product.getImages().stream()
                .map(image -> new ProductImageResponse(image.getId(), image.getUrl()))
                .toList();

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                imageResponses,
                categoryResponseMapper.mapToCategoryResponseListWithIdAndName(product.getCategories())
        );
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
