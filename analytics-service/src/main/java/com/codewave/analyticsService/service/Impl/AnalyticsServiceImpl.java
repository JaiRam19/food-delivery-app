package com.codewave.analyticsService.service.Impl;

import com.codewave.analyticsService.dto.*;
import com.codewave.analyticsService.service.AnalyticsService;
import com.codewave.analyticsService.service.OrderClient;
import com.codewave.analyticsService.service.ProductClient;
import com.codewave.analyticsService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service("Traditional")
@NoArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private OrderClient orderClient;
    private UserService userService;
    private ProductClient productClient;

    @Autowired
    public AnalyticsServiceImpl(OrderClient orderClient, UserService userService, ProductClient productClient) {
        this.orderClient = orderClient;
        this.userService = userService;
        this.productClient = productClient;
    }

    @Override
    public List<CustomResponse> top10UsersBasedOnTheirTotalOrderValue() {

        //get all user id's and their corresponding total order value from order-service
        CompletableFuture<Map<Long, Double>> userIdWithTotalOrderValueFuture = CompletableFuture.supplyAsync(() -> orderClient.getAllOrders().stream()
                .collect(Collectors.groupingBy(OrderDto::getUserId, Collectors.summingDouble(OrderDto::getTotalAmount))));

        //get all users from user-service
        CompletableFuture<List<UserDto>> usersFuture = CompletableFuture.supplyAsync(() -> userService.findAllUsers());

        //combine two individual above futures
        CompletableFuture<List<CustomResponse>> result = userIdWithTotalOrderValueFuture.thenCombine(usersFuture, (userIdWithTotalOrderValue, users) ->
                userIdWithTotalOrderValue.entrySet().stream()
                        .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                        .map(order -> new CustomResponse(getUserName(users, order.getKey()) + ", " + roundOffTwoDigits(order.getValue())))
                        .limit(10)
                        .toList());
        return result.join();
    }

    @Override
    public CompletableFuture<CustomResponse> topProductByCategory(String category) {
        //get list of products under the given category from product-service
        CompletableFuture<List<ProductDto>> productsFuture = CompletableFuture.supplyAsync(() -> productClient.findByCategory(category));

        //extract product ids
        CompletableFuture<List<Long>> productIdsFuture = productsFuture.thenApply(products -> products.stream()
                .map(ProductDto::getProductId)
                .toList()
        );

        //count the no of orders by product id
        CompletableFuture<Map<Long, Long>> orderFuture = productIdsFuture.thenCompose(productIds ->
                CompletableFuture.supplyAsync(() -> orderClient.getProductsForGivenIds(productIds).stream()
                        .collect(Collectors.groupingBy(OrderItemDto::getProductId, Collectors.counting()))
                )
        );

        //get products from product future
        List<ProductDto> listOfProducts = productsFuture.join();


        //combine the results
        return productsFuture.thenCompose(products ->
                orderFuture.thenApply(orderDetails -> orderDetails.entrySet().stream()
                        .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                        .map(topProducts -> new CustomResponse("Top product : " + findProductNameById(products, topProducts.getKey()) + " with total orders " + topProducts.getValue()))
                        .findFirst().orElse(null)));
    }

    protected String findProductNameById(List<ProductDto> listOfProducts, Long productId) {
        return listOfProducts.stream()
                .filter(productDto -> Objects.equals(productDto.getProductId(), productId))
                .map(ProductDto::getProductName)
                .collect(Collectors.joining());
    }

    private String getUserName(List<UserDto> listOfUsers, Long userId) {
        return listOfUsers.stream()
                .filter(user -> Objects.equals(user.getUserId(), userId))
                .map(user -> user.getFirstName() + " " + user.getLastName())
                .collect(Collectors.joining());
    }

    private Double roundOffTwoDigits(Double amount) {
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
