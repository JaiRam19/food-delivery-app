package com.codewave.analyticsService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDER_ANALYTICS")
public class OrderAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Double amount;

    private String status;

    @Column(nullable = false)
    private String eventType;  // ORDER_CREATED, ORDER_DELIVERED, etc.

    // Order-specific fields
    private Long addressId;
    private Integer itemCount;
    private String restaurantName;

    @Column(nullable = false)
    private LocalDateTime eventTimestamp;

    @Column(nullable = false)
    private LocalDateTime processedAt;
}
