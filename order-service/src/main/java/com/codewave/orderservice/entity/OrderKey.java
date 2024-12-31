package com.codewave.orderservice.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderKey implements Serializable {
    private Long userId;
    private Long orderId;
}
