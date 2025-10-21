package com.codewave.orderservice.service.Impl;

import com.codewave.events.OrderEvent;
import com.codewave.orderservice.client.AddressClient;
import com.codewave.orderservice.client.ProductClient;
import com.codewave.orderservice.client.UserClient;
import com.codewave.orderservice.dto.OrderRequest;
import com.codewave.orderservice.entity.Order;
import com.codewave.orderservice.entity.OrderItems;
import com.codewave.orderservice.entity.OrderStatus;
import com.codewave.orderservice.exception.APIException;
import com.codewave.orderservice.kafka.producer.OrderEventProducer;
import com.codewave.orderservice.repository.OrderItemsRepository;
import com.codewave.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderCreationService {

    private OrderRepository repository;
    private OrderItemsRepository itemsRepository;
    private UserClient userClient;
    private AddressClient addressClient;
    private ProductClient productClient;
    private OrderEventProducer orderEventProducer;
    private OrderServiceImpl orderService;

    @Transactional
    public String createOrder(OrderRequest request) {
        log.info("OrderCreationService:createOrder() method execution started...");

        //verify user
        Boolean isValidUser = userClient.isValidUser(request.getUserId());

        //verify user address
        Boolean isValidUserAddress = addressClient.isValidAddress(request.getUserId(), request.getAddressId());

        //calculate products total amount
        Double totalAmount = productClient.calculateTotal(request.getItems());

        //create Order object to save into db
        try {
            if (isValidUser && isValidUserAddress) {
                Order order = new Order();
                order.setUserId(request.getUserId());
                order.setAddressId(request.getAddressId());
                order.setTotalAmount(totalAmount);
                order.setStatus(OrderStatus.PENDING);
                order.setCreatedAt(LocalDateTime.now());
                order.setUpdatedAt(LocalDateTime.now());

                //save order
                Order savedOrder = repository.save(order);

                //save order items
                //save the order items
                List<OrderItems> orderItems = request.getItems().stream()
                        .map(orderedItem -> new OrderItems(savedOrder,
                                orderedItem.getProductId(),
                                orderedItem.getQuantity()))
                        .toList();

                List<OrderItems> savedOrderItems = itemsRepository.saveAll(orderItems);


                //publish to kafka
                OrderEvent orderEvent = new OrderEvent();
                orderEvent.setOrderId(savedOrder.getId());
                orderEvent.setUserId(UUID.randomUUID());
                orderEvent.setAddressId(savedOrder.getAddressId());
                orderEvent.setStatus(savedOrder.getStatus().getStatus());
                orderEvent.setEventType("ORDER_CREATED");
                orderEvent.setAmount(savedOrder.getTotalAmount());
                orderEvent.setItemCount(savedOrderItems.size());
                orderEvent.setTimestamp(LocalDateTime.now());
                orderEventProducer.publishOrderCreatedEvent(orderEvent);
            } else {
                log.error("User or Address is not valid");
                throw new APIException("User Id or Address Id is not valid please provide correct inputs...");
            }
        }catch (Exception e){
            throw new APIException(e.getMessage());
        }

        log.info("OrderCreationService:createOrder() method execution ended...");
        return "Order placed successfully!!!";
    }
}
