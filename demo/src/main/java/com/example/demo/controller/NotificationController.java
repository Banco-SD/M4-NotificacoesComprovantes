package com.example.demo.controller;

import com.example.demo.dto.ReceiptDTO;
import com.example.demo.dto.TransactionCompletedEvent;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final ReceiptService receiptService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/receipt/{transactionId}")
    public ResponseEntity<ReceiptDTO> downloadReceipt(@PathVariable UUID transactionId) {
        return receiptService.findByTransactionId(transactionId)
                .map(receipt -> ReceiptDTO.builder()
                        .transactionId(receipt.getTransactionId())
                        .fileName(receipt.getFileName())
                        .downloadUrl("/notifications/receipt/download/" + receipt.getTransactionId())
                        .generatedAt(receipt.getGeneratedAt())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<Void> resendNotification(@PathVariable UUID id) {
        notificationService.resendNotification(id);
        return ResponseEntity.ok().build();
    }

    // Importe essa classe no topo se faltar: com.example.demo.dto.TransactionCompletedEvent;
// Importe também: java.math.BigDecimal; java.time.LocalDateTime;

@PostMapping("/teste-local")
public ResponseEntity<String> testarFluxoLocal() {
    TransactionCompletedEvent eventoFake = new TransactionCompletedEvent();
    eventoFake.setTransactionId(UUID.randomUUID());
    eventoFake.setUserId(UUID.randomUUID());
    eventoFake.setEmail("aluno@faculdade.com");
    eventoFake.setPhone("11999999999");
    eventoFake.setDestination("Conta Destino Teste");
    eventoFake.setAmount(new BigDecimal("150.00"));
    eventoFake.setTimestamp(LocalDateTime.now());

    // Força o service a processar como se tivesse vindo da fila!
    notificationService.processTransaction(eventoFake);

    return ResponseEntity.ok("Fluxo executado! Verifique o console do VS Code e o banco de dados.");
}
}