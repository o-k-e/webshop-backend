package com.ganesha.webshop.service.mapper;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewProductMapper {

    public Product mapToProduct(NewProductRequest newProductRequest, List<Category> categories) {
        Product product = new Product();
        product.setProductName(newProductRequest.productName());
        product.setProductDescription(newProductRequest.description());
        product.setPrice(newProductRequest.price());
        product.setCategories(new ArrayList<>(categories));

        List<ProductImage> images = newProductRequest.imageFileNames().stream().map(fileName -> {
            ProductImage image = new ProductImage();
            image.setUrl(fileName);
            image.setProduct(product);
            return image;
        }).collect(Collectors.toList());

        product.setImages(new ArrayList<>(images));
        return product;
    }
}
