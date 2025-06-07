package com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources;

import java.time.Instant;

public record NotificationResource(
        Long id,
        String type,
        String recipient,
        String payload,
        String status,
        Instant sentAt,
        Long orderId,
        Long invoiceId,
        Long paymentId,
        Instant createdAt,
        Instant updatedAt
) { }