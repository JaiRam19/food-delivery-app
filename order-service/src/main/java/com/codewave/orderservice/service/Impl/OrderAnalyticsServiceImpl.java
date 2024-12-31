package com.codewave.orderservice.service.Impl;

import com.codewave.orderservice.dto.CustomResponse;
import com.codewave.orderservice.entity.OrderItems;
import com.codewave.orderservice.repository.OrderItemsRepository;
import com.codewave.orderservice.service.OrderAnalyticsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderAnalyticsServiceImpl implements OrderAnalyticsService {

    private OrderItemsRepository orderItemsRepository;
    @Override
    public List<CustomResponse> getTotalCountAndSale(LocalDate fromDate, LocalDate toDate) {
        Map<Long, Long> productIdAndCount = orderItemsRepository.findByOrderItemsBetween(fromDate, toDate).stream()
                .collect(Collectors.groupingBy(OrderItems::getProductId, Collectors.counting()));

        //get product name and price from product service by passing product id
        //for time being hard code the values
        String product1 = "Biriyani";

        return productIdAndCount.entrySet().stream()
                .map(orderDetails -> new CustomResponse("Product Name: " + product1 + ", Product Id: "+orderDetails.getKey()+", Total orders: " + orderDetails.getValue() + ", Total Sales: " + calculateTotalSales(orderDetails.getValue())))
                .toList();

    }

    private Double calculateTotalSales(Long orderCount){
        Double price1 = 230.0;
        return orderCount * price1;
    }
}
