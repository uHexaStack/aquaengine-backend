package com.qu3dena.aquaengine.backend.notification.application.internal.outboundservices;

public interface NotificationSender {
    boolean send(String recipient, String payload);
}