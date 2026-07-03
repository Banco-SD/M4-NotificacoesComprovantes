package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReceiptDTO {
    private UUID transactionId;
    private String fileName;
    private String downloadUrl;
    private LocalDateTime generatedAt;
}