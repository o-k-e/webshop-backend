package com.ganesha.webshop.repository;

import com.ganesha.webshop.model.entity.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

}
