package com.qu3dena.aquaengine.backend.notification.domain.model.commands;

/**
 * Represents a command to send a notification.
 * <p>
 * This command contains the necessary information required to send a notification,
 * including the type, recipient, payload, and optional order, invoice, and payment IDs.
 * </p>
 *
 * @param type      the type of notification
 * @param recipient the recipient of the notification
 * @param payload   the message content or payload of the notification
 * @param orderId   the associated order ID (if any)
 * @param invoiceId the associated invoice ID (if any)
 * @param paymentId the associated payment ID (if any)
*/
public record SendNotificationCommand(
        String type,
        String recipient,
        String payload,
        Long orderId,
        Long invoiceId,
        Long paymentId
) {
    public SendNotificationCommand {
        if (type == null || type.isBlank())
            throw new IllegalArgumentException("Type cannot be null or blank");

        if (recipient == null || recipient.isBlank())
            throw new IllegalArgumentException("Recipient cannot be null or blank");

        if (payload == null || payload.isBlank())
            throw new IllegalArgumentException("Payload cannot be null or blank");
    }
}