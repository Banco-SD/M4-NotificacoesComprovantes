package com.example.demo.service;

import com.example.demo.dto.TransactionCompletedEvent;
import com.example.demo.entity.Receipt;
import com.example.demo.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public Receipt generateReceipt(TransactionCompletedEvent event) {
        Receipt receipt = Receipt.builder()
                .transactionId(event.getTransactionId())
                .fileName("comprovante_" + event.getTransactionId() + ".pdf")
                .filePath("/storage/receipts/")
                .generatedAt(LocalDateTime.now())
                .build();
        receipt.generateHash();
        return receiptRepository.save(receipt);
    }

    public byte[] generatePdf(Receipt receipt) {
        // Simulação acadêmica de geração de bytes de um PDF
        return ("COMPROVANTE FISCAL DE TRANSAÇÃO\nID: " + receipt.getTransactionId() + "\nHash de Segurança: " + receipt.getHash()).getBytes();
    }

    public Receipt save(Receipt receipt) { return receiptRepository.save(receipt); }
    public Optional<Receipt> findByTransactionId(UUID transactionId) { return receiptRepository.findByTransactionId(transactionId); }
}