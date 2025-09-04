package com.ganesha.webshop.repository;

import com.ganesha.webshop.model.entity.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByProductNameContainingIgnoreCase(String query);

    @Query("SELECT DISTINCT p.productName FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<String> findTop10ProductNames(@Param("query") String query, Pageable pageable);}
