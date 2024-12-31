package com.codewave.orderservice.repository;

import com.codewave.orderservice.entity.OrderItems;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Registered
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    @Query("SELECT o FROM OrderItems o WHERE o.order.id = :orderId")
    List<OrderItems> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT o FROM OrderItems o WHERE FUNCTION('DATE', o.order.createdAt) BETWEEN :fromDate AND :toDate")
    List<OrderItems> findByOrderItemsBetween(@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
    //@Query("SELECT o FROM OrderItems o WHERE o.productId IN :productIds")
    List<OrderItems> findByProductIdIn(List<Long> productIds);
}
