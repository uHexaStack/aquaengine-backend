package com.qu3dena.aquaengine.backend.notification.infrastructure.notification;

import com.qu3dena.aquaengine.backend.notification.application.internal.outboundservices.NotificationSender;
import org.springframework.stereotype.Service;

/**
 * SMTPNotificationSender implements NotificationSender to send notifications via SMTP.
 * <p>
 * This service is managed by Spring and provides an implementation for sending notifications.
 * </p>
 */
@Service
public class SMTPNotificationSender implements NotificationSender {

    /**
     * Sends a notification using SMTP or SMS.
     *
     * @param recipient the recipient of the notification
     * @param payload the content of the notification
     * @return {@code true} if the notification is sent successfully, {@code false} otherwise
     */
    @Override
    public boolean send(String recipient, String payload) {
        // TODO: implement SMTP or SMS sending logic
        return true;
    }
}