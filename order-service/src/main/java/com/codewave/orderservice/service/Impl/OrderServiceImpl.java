package com.codewave.orderservice.service.Impl;

import com.codewave.orderservice.dto.*;
import com.codewave.orderservice.entity.Order;
import com.codewave.orderservice.entity.OrderItems;
import com.codewave.orderservice.entity.OrderStatus;
import com.codewave.orderservice.exception.APIException;
import com.codewave.orderservice.mapper.OrderItemMapper;
import com.codewave.orderservice.mapper.OrderMapper;
import com.codewave.orderservice.repository.OrderItemsRepository;
import com.codewave.orderservice.repository.OrderRepository;
import com.codewave.orderservice.service.AddressClient;
import com.codewave.orderservice.service.OrderService;
import com.codewave.orderservice.service.ProductClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemsRepository orderItemsRepository;
    private ProductClient productClient;
    private AddressClient addressClient;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;


    @Override
    public OrderResponse receiveOrder(OrderRequest orderRequest) {
        log.info("OrderServiceImpl:receiveOrder method execution started...");
        log.info("input items in orderRequest {}", orderRequest.getItems());
        //get all product details from product service
        CompletableFuture<List<ProductDto>> orderedProductsFuture = CompletableFuture.supplyAsync(() -> orderRequest.getItems().stream()
                .map(order ->
                {
                    try {
                        return productClient.getProduct(order.getProductId());
                    } catch (APIException exception) {
                        throw new APIException(exception.getErrorResponse());
                    }
                })
                .collect(Collectors.toList()));

        //verify if the product quantity is sufficient with requested quantity
        orderedProductsFuture.thenApply(products ->
                products.stream()
                        .collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getStock))

        ).thenCompose(productIdsAndStock -> CompletableFuture.supplyAsync(() ->
                orderRequest.getItems().stream()
                        .peek(order ->
                        {
                            Long availableProductQuantity = productIdsAndStock.get(order.getProductId());
                            if (availableProductQuantity == null || availableProductQuantity < order.getQuantity()) {
                                throw new APIException("Insufficient quantity...");
                            }
                        })
                        .collect(Collectors.toList())
        ));

        //get Address from address-service for given id
        CompletableFuture.supplyAsync(() -> {
            try {
                return addressClient.getAddressByUserAndAddressIds(orderRequest.getUserId(), orderRequest.getAddressId());
            } catch (APIException exception) {
                log.error("OrderServiceImpl::receiveOrder catch server exception {}", exception.getMessage());
                throw new APIException(exception.getErrorResponse());
            }
        });


        //collect order details first
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setAddressId(orderRequest.getAddressId());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setUpdatedAt(LocalDateTime.now());

        //calculate the total amount
        Double totalAmount = orderedProductsFuture.thenApply(orderedProducts -> orderRequest.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * findProduct(item.getProductId(), orderedProducts).getPrice())
                .sum()).join();
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        //save the order items
        List<OrderItems> orderItems = orderRequest.getItems().stream()
                .map(orderedItem -> new OrderItems(savedOrder,
                        orderedItem.getProductId(),
                        orderedItem.getQuantity()))
                .toList();
        orderItemsRepository.saveAll(orderItems);
        log.info("OrderServiceImpl:receiveOrder method execution ended...");
        return mapToOrderResponse(savedOrder.getId());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return mapToOrderResponse(id);
    }

    @Override
    public List<OrderResponse> getAllOrdersById(Long id) {
        System.out.println(orderRepository.findAllOrderIdsByUserId(id));
        return orderRepository.findAllOrderIdsByUserId(id).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Order Canceled successfully";
    }

    @Override
    public List<OrderResponse> getOrdersBetweenTwoDates(Long userId, LocalDate fromDate, LocalDate toDate) {
        return orderRepository.findByCreatedAtDateBetweenForSpecificUser(userId, fromDate, toDate).stream()
                .map(order -> mapToOrderResponse(order.getId())).toList();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        log.info("OrderServiceImpl:getAllOrders method execution started...");
        List<OrderDto> list = orderRepository.findAll().stream()
                .map(order -> orderMapper.mapToDto(order))
                .toList();
        log.info("OrderServiceImpl:getAllOrders method execution ended...");
        return list;
    }

    @Override
    public List<OrderItemDto> getOrderItemDetailsForGiveProductIds(List<Long> productsIds) {
        return orderItemsRepository.findByProductIdIn(productsIds).stream()
                .map(orderItems -> orderItemMapper.mapToDto(orderItems))
                .toList();
    }

    public OrderResponse mapToOrderResponse(Long orderId) {
        Order savedOrder = orderRepository.findById(orderId).get();

        //Order response
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(savedOrder.getId());
        orderResponse.setStatus(savedOrder.getStatus());

        //convert OrderItems to OrderItem
        List<OrderItem> orderedItems = orderItemsRepository.findByOrderId(savedOrder.getId()).stream()
                .map(savedItem -> {
                    ProductDto productDto = productClient.getProduct(savedItem.getProductId());
                    return new OrderItem(savedItem.getProductId(),
                            productDto.getProductName(),
                            productDto.getPrice(),
                            savedItem.getQuantity(),
                            (productDto.getPrice() * savedItem.getQuantity()));
                }).toList();

        orderResponse.setOrderItems(orderedItems);
        Double grandTotal = orderedItems.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        orderResponse.setGrandTotal(grandTotal);
        orderResponse.setCreatedAt(savedOrder.getCreatedAt());
        orderResponse.setUpdatedAt(savedOrder.getUpdatedAt());

        //get address details from address-service
        orderResponse.setAddressDto(addressClient.getAddressByUserAndAddressIds(savedOrder.getUserId(), savedOrder.getAddressId()));
        return orderResponse;
    }

    private ProductDto findProduct(Long productId, List<ProductDto> listOfProducts) {
        return listOfProducts.stream()
                .filter(product -> Objects.equals(product.getProductId(), productId))
                .findFirst().get();
    }


}
