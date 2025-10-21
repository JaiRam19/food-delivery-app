package com.codewave.orderservice.kafka.producer;

import com.codewave.events.OrderEvent;
import com.codewave.orderservice.exception.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-events";

    public void publishOrderCreatedEvent(OrderEvent order){

        try{
            // Send message with order ID as key (for partitioning)
            CompletableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate
                    .send(TOPIC, order.getOrderId().toString(), order);

            // Handle success/failure asynchronously
            future.whenComplete((result, ex) -> {
                if(ex == null){
                    log.info("order event published successfully: {}, to partition: {}",
                            order.getEventType(),
                            result.getProducerRecord().partition());
                }else{
                    log.error("Failed to publish order event: {}", order.getEventType());
                }
            });

        }catch (Exception e){
            throw new APIException("Exception occurred while publishing order event: "+order.getEventType());
        }
    }
}
