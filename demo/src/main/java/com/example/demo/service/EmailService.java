package com.example.demo.service;

import com.example.demo.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getRecipient());
        message.setSubject("Notificação de Transação");
        message.setText(notification.getMessage());
        mailSender.send(message);
    }

    public void sendReceipt(Notification notification, Receipt receipt) {
        // Mock ou envio real com anexo usando MimeMessage
        System.out.println("Enviando e-mail com comprovante em anexo para: " + notification.getRecipient() + " [Arquivo: " + receipt.getFileName() + "]");
    }
}