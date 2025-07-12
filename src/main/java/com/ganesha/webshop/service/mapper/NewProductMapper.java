package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewProductMapper {

    public Product mapToEntity(NewProductRequest newProductRequest, Category category) {
        Product product = new Product();
        product.setProductName(newProductRequest.productName());
        product.setProductDescription(newProductRequest.description());
        product.setPrice(newProductRequest.price());
        product.setCategories(List.of(category));

        ProductImage image = new ProductImage();
        image.setUrl(newProductRequest.imageFileName());
        image.setProduct(product);
        product.setImages(List.of(image));

        return product;
    }
}
