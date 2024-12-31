package com.codewave.analyticsService.service;

import com.codewave.analyticsService.dto.OrderDto;
import com.codewave.analyticsService.dto.OrderItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:8080", value = "ORDER-SERVICE")
public interface OrderClient {

    @GetMapping("/api/orders/all-orders")
    List<OrderDto> getAllOrders();

    @PostMapping("/api/orders/product-list")
    List<OrderItemDto> getProductsForGivenIds(@RequestBody List<Long> productIds);
}
