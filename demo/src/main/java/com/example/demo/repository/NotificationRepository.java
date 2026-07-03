package com.example.demo.repository;

import com.example.demo.entity.Notification;
import com.example.demo.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Optional<Notification> findByTransactionId(UUID transactionId);
    List<Notification> findByUserId(UUID userId);
    List<Notification> findByStatus(NotificationStatus status);
}