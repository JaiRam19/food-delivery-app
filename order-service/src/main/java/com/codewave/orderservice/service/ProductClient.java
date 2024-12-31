package com.codewave.orderservice.service;

import com.codewave.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8083", value = "PRODUCT-SERVICE")
public interface ProductClient {

    //get product by id
    @GetMapping("/api/products/{productId}")
    ProductDto getProduct(@PathVariable("productId") Long productId);

}
