package com.qu3dena.aquaengine.backend.notification.interfaces.acl;

import java.util.List;

public interface NotificationContextFacade {
    Long sendNotification(String type,
                          String recipient,
                          String payload,
                          Long orderId,
                          Long invoiceId,
                          Long paymentId);

    List<Long> getNotificationsByStatus(String status);

    List<Long> getNotificationsByRecipient(String recipient);
}