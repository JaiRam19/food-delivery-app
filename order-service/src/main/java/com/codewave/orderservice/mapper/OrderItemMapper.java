package com.codewave.orderservice.mapper;

import com.codewave.orderservice.dto.OrderDto;
import com.codewave.orderservice.dto.OrderItemDto;
import com.codewave.orderservice.entity.Order;
import com.codewave.orderservice.entity.OrderItems;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderItemDto mapToDto(OrderItems orderDetails) {
        return modelMapper.map(orderDetails, OrderItemDto.class);
    }

    private OrderItems mapToEntity(OrderItemDto orderItemDto) {
        return modelMapper.map(orderItemDto, OrderItems.class);
    }
}
