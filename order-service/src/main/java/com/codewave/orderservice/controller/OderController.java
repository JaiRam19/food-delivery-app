package com.codewave.orderservice.controller;

import com.codewave.orderservice.dto.OrderDto;
import com.codewave.orderservice.dto.OrderItemDto;
import com.codewave.orderservice.dto.OrderRequest;
import com.codewave.orderservice.dto.OrderResponse;
import com.codewave.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@AllArgsConstructor
public class OderController {

    private OrderService orderService;

    //place order
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse order = orderService.receiveOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    //get order by id
    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        OrderResponse order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    //get all orders for a user
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForAUser(@PathVariable("userId") Long userId) {
        System.out.println(userId);
        List<OrderResponse> allOrders = orderService.getAllOrdersById(userId);
        return ResponseEntity.ok(allOrders);
    }

    //cancel order
    @GetMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {
        String cancelOrder = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(cancelOrder, HttpStatus.OK);
    }

    //find orders between two dates
    @GetMapping("/filter-orders")
    public ResponseEntity<List<OrderResponse>> findOrdersBetweenTwoDate(@RequestParam("userId") Long userId,
                                                                        @RequestParam("fromDate") LocalDate fromDate,
                                                                        @RequestParam("toDate") LocalDate toDate) {
        List<OrderResponse> filteredOrders = orderService.getOrdersBetweenTwoDates(userId, fromDate, toDate);
        return ResponseEntity.ok(filteredOrders);
    }

    //get all orders
    @GetMapping("all-orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    //get all order items for given product ids
    @PostMapping("product-list")
    public ResponseEntity<List<OrderItemDto>> getProductsForGivenIds(@RequestBody List<Long> productIds){
        return ResponseEntity.ok(orderService.getOrderItemDetailsForGiveProductIds(productIds));
    }

}
