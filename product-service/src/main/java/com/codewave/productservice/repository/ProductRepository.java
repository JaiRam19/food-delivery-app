package com.codewave.productservice.repository;

import com.codewave.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String productName);
    List<Product> findByProductCategory(String category);

    @Query("SELECT p.productCategory FROM Product p WHERE p.productId = :productId")
    Optional<String> findProductCategoryByProductId(@Param("productId") Long productId);
}

