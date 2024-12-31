package com.codewave.orderservice.dto;

import com.codewave.orderservice.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private Double grandTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AddressDto addressDto;
}
