package com.codewave.productservice.service;

import com.codewave.productservice.dto.ProductDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    ProductDto getProductById(Long productId, Jwt jwt);
    List<ProductDto> getAllProducts();
    String deleteProductById(Long productId);
    List<ProductDto> addMoreProducts(List<ProductDto> productDto);
    List<ProductDto> applyDiscount(int discountPercentage, String category, Long applicableQuantity);
    List<ProductDto> applyFlatPriceReduction(String category, Double flatPrice);
    List<ProductDto> findProductsByCategory(String category);
}
