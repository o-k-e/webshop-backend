package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResponseMapper {

    public ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice()
        );
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
