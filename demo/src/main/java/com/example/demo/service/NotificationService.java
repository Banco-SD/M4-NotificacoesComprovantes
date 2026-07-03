package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.enums.*;
import com.example.demo.dto.TransactionCompletedEvent;
import com.example.demo.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ReceiptService receiptService;
    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushNotificationService;

    public void processTransaction(TransactionCompletedEvent event) {
        Notification notification = createNotification(event);
        notification.markAsProcessing();
        notificationRepository.save(notification);

        Receipt receipt = receiptService.generateReceipt(event);
        sendNotificationWithReceipt(notification, receipt);
    }

    public Notification createNotification(TransactionCompletedEvent event) {
        return Notification.builder()
                .userId(event.getUserId())
                .transactionId(event.getTransactionId())
                .type(NotificationType.EMAIL)
                .channel(NotificationChannel.EMAIL)
                .recipient(event.getEmail())
                .message("Olá! Sua transação de R$ " + event.getAmount() + " foi realizada com sucesso.")
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void sendNotificationWithReceipt(Notification notification, Receipt receipt) {
        try {
            emailService.sendReceipt(notification, receipt);
            notification.markAsSent();
        } catch (Exception e) {
            notification.markAsFailed();
        }
        notificationRepository.save(notification);
    }

    public void sendNotification(Notification notification) {
        try {
            if (notification.getChannel() == NotificationChannel.EMAIL) emailService.sendEmail(notification);
            else if (notification.getChannel() == NotificationChannel.SMS) smsService.sendSms(notification);
            else pushNotificationService.sendPush(notification);
            notification.markAsSent();
        } catch (Exception e) {
            notification.markAsFailed();
        }
        notificationRepository.save(notification);
    }

    public Notification save(Notification notification) { return notificationRepository.save(notification); }
    public Optional<Notification> findById(UUID id) { return notificationRepository.findById(id); }
    public List<Notification> getUserNotifications(UUID userId) { return notificationRepository.findByUserId(userId); }

    public void resendNotification(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        sendNotification(notification);
    }
}