package com.codewave.events;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
    private Long orderId;
    private UUID userId;
    private String eventType;
    private Double amount;
    private String status;
    private LocalDateTime timestamp;
    private Long addressId;
    private Integer itemCount;
}
