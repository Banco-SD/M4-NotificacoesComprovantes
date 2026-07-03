package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.TransactionType;

@Data
public class TransactionCompletedEvent {
    private UUID transactionId;
    private UUID userId;
    private String email;
    private String phone;
    private String destination;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime timestamp;
}