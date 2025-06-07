package com.qu3dena.aquaengine.backend.notification.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.notification.domain.model.aggregates.NotificationAggregate;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResource(NotificationAggregate entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getType().getName().name(),
                entity.getRecipient(),
                entity.getPayload(),
                entity.getStatus().getName().name(),
                entity.getSentAt(),
                entity.getOrderId(),
                entity.getInvoiceId(),
                entity.getPaymentId(),
                entity.getCreatedAt().toInstant(),
                entity.getUpdatedAt().toInstant()
        );
    }
}