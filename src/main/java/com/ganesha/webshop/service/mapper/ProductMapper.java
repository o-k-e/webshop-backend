package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.entity.products.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public Product mapToProduct(ProductResponse productResponse) {
        Product product = new Product();
        product.setId(productResponse.id());
        product.setProductName(productResponse.productName());
        product.setProductDescription(productResponse.productDescription());
//        product.setCategories(productResponse);

        return product;
    }

    public List<Product> mapToProducts(List<ProductResponse> productResponses) {
        return productResponses.stream()
                .map(this::mapToProduct)
                .toList();
    }
}
