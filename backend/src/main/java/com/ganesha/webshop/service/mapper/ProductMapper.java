//package com.ganesha.webshop.service.mapper;
//
//import com.ganesha.webshop.model.dto.response.ProductResponseWithFilteredCategories;
//import com.ganesha.webshop.model.entity.product.Product;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class ProductMapper {
//
//    public Product mapToProduct(ProductResponseWithFilteredCategories productResponse) {
//        Product product = new Product();
//        product.setId(productResponse.id());
//        product.setProductName(productResponse.productName());
//        product.setProductDescription(productResponse.productDescription());
//        product.setPrice(productResponse.price());
////        product.setCategories(productResponse);
//
//        return product;
//    }
//
//    public List<Product> mapToProductList(List<ProductResponseWithFilteredCategories> productResponses) {
//        return productResponses.stream()
//                .map(this::mapToProduct)
//                .toList();
//    }
//}
