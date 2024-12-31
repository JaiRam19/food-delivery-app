package com.codewave.orderservice.repository;

import com.codewave.orderservice.entity.Order;
import lombok.NonNull;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id FROM Order o WHERE o.userId = :userId")
    List<Long> findAllOrderIdsByUserId(@Param("userId") Long userId);

    boolean existsById(Long orderId);

    @Query(value = "SELECT o FROM Order o WHERE FUNCTION('DATE', o.createdAt) BETWEEN :fromDate AND :endDate AND o.userId = :userId")
    List<Order> findByCreatedAtDateBetweenForSpecificUser(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("endDate") LocalDate endDate);
    //List<Order> findByCreatedAtDateBetween(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("endDate") LocalDate endDate);
}
