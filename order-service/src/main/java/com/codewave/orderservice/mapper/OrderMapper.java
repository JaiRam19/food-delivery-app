package com.codewave.orderservice.mapper;

import com.codewave.orderservice.dto.OrderDto;
import com.codewave.orderservice.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderDto mapToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private Order mapToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}
