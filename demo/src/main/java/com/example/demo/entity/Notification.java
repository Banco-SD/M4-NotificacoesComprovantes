package com.example.demo.entity;

import com.example.demo.enums.NotificationChannel;
import com.example.demo.enums.NotificationStatus;
import com.example.demo.enums.NotificationType;
import com.example.demo.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID transactionId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Column(nullable = false)
    private String recipient;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public void markAsProcessing() { this.status = NotificationStatus.PROCESSING; }
    public void markAsSent() { this.status = NotificationStatus.SENT; this.sentAt = LocalDateTime.now(); }
    public void markAsFailed() { this.status = NotificationStatus.FAILED; }
}