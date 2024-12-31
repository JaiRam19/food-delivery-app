package com.codewave.orderservice.dto;

import com.codewave.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private Long orderItemId;
    private Order order;
    private Long productId;
    private Long quantity;
}
