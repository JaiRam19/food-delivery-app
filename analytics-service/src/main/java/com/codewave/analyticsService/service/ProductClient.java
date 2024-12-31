package com.codewave.analyticsService.service;

import com.codewave.analyticsService.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//instead of calling direct url we can call it using service-name as the product service is
//registered in eureka server, as this analytics service is an eureka client by default contains the load balancer it
//will call the available instance automatically
//@FeignClient(url = "http://localhost:8083", value = "PRODUCT-SERVICE")
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/api/products/all-products/category/{category}")
    List<ProductDto> findByCategory(@PathVariable("category") String category);
}
