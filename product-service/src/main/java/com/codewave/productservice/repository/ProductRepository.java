package com.codewave.productservice.repository;

import com.codewave.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String productName);
    List<Product> findByProductCategory(String category);
}

