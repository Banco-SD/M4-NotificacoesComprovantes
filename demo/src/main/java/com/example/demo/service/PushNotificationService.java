package com.example.demo.service;

import com.example.demo.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    public void sendPush(Notification notification) {
        System.out.println("[PUSH] Enviado para o dispositivo do Usuário: " + notification.getMessage());
    }
}