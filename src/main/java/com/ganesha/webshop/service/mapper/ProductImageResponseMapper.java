package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.response.ProductImageResponse;
import com.ganesha.webshop.model.entity.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductImageResponseMapper {

    public List<ProductImageResponse> mapToListOfProductImageResponse(Product product) {
        return product.getImages().stream()
                .map(image -> new ProductImageResponse(image.getId(), image.getUrl()))
                .toList();
    }
}
