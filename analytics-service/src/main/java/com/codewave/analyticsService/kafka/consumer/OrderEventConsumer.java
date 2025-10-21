package com.codewave.analyticsService.kafka.consumer;

import com.codewave.analyticsService.entity.OrderAnalytics;
import com.codewave.analyticsService.repository.OrderAnalyticsRepository;
import com.codewave.events.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderAnalyticsRepository repository;

    @KafkaListener(
            groupId = "analytics-service-group",
            topics = "order-events"
    )
    public void consumeOrderEvent(OrderEvent event){

        log.info("Received order event: {} for Order ID: {}", event.getEventType(), event.getOrderId());

        try{
            switch (event.getEventType()){
                case "ORDER_CREATED" -> handleOrderPending(event);
                case "ORDER_CONFIRMED" -> handleOrderConfirmed(event);
                default -> log.warn("⚠️ Unknown event type: {}", event.getEventType());
            }
        }catch (Exception e){
            throw new RuntimeException("Exception occurred while consuming order events -> "+e.getMessage());
        }
    }

    private void handleOrderConfirmed(OrderEvent event) {
    }

    private void handleOrderPending(OrderEvent event) {
        log.info("💾 Saving ORDER_CREATED analytics...");

        OrderAnalytics analytics = new OrderAnalytics();
        analytics.setOrderId(event.getOrderId());
        analytics.setUserId(event.getUserId());
        analytics.setAmount(event.getAmount());
        analytics.setStatus(event.getStatus());
        analytics.setEventType(event.getEventType());
        analytics.setAddressId(event.getAddressId());
        analytics.setItemCount(event.getItemCount());
        analytics.setEventTimestamp(event.getTimestamp());
        analytics.setProcessedAt(LocalDateTime.now());

        repository.save(analytics);

        log.info("💾 Saved ORDER_CREATED analytics for order: {}", event.getOrderId());
    }
}
