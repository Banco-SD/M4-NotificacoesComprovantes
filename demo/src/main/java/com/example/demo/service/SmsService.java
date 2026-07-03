package com.example.demo.service;

import com.example.demo.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(Notification notification) {
        System.out.println("[SMS] Enviado para " + notification.getRecipient() + ": " + notification.getMessage());
    }
}