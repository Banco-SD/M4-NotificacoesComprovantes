package com.example.demo.consumer;

import com.example.demo.dto.TransactionCompletedEvent;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "${amqp.queue.transaction-completed}")
    public void consumeTransactionCompleted(TransactionCompletedEvent event) {
        System.out.println("Evento recebido da fila de transações: " + event.getTransactionId());
        notificationService.processTransaction(event);
    }
}