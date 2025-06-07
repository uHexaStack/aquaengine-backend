package com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources;

public record CreateNotificationResource(
        String type,
        String recipient,
        String payload,
        Long orderId,
        Long invoiceId,
        Long paymentId
) { }