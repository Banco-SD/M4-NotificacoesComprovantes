package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "receipts")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID notificationId;

    @Column(nullable = false)
    private UUID transactionId;

    private String fileName;
    private String filePath;
    private String hash;
    private LocalDateTime generatedAt;

    public void generateHash() {
        this.hash = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}