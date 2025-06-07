package com.qu3dena.aquaengine.backend.notification.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;
import com.qu3dena.aquaengine.backend.notification.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
    public static SendNotificationCommand toCommand(CreateNotificationResource resource) {
        return new SendNotificationCommand(
                resource.type(),
                resource.recipient(),
                resource.payload(),
                resource.orderId(),
                resource.invoiceId(),
                resource.paymentId()
        );
    }
}