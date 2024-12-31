package com.codewave.orderservice.service;

import com.codewave.orderservice.dto.OrderDto;
import com.codewave.orderservice.dto.OrderItemDto;
import com.codewave.orderservice.dto.OrderRequest;
import com.codewave.orderservice.dto.OrderResponse;
import com.codewave.orderservice.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderService {
    OrderResponse receiveOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getAllOrdersById(Long id);
    String cancelOrder(Long orderId);
    List<OrderResponse> getOrdersBetweenTwoDates(Long userId, LocalDate fromDate, LocalDate toDate);
    List<OrderDto> getAllOrders();
    List<OrderItemDto> getOrderItemDetailsForGiveProductIds(List<Long> productsIds);
}
