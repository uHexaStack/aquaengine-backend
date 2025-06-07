package com.qu3dena.aquaengine.backend.billing.domain.model.events;

/**
 * Represents an event indicating that an invoice operation has failed.
 * <p>
 * This event encapsulates key details about the failure including the associated order ID and the reason for failure.
 * </p>
 *
 * @param orderId the identifier of the order associated with the failed invoice
 * @param reason  the reason why the invoice operation failed
 */
public record InvoiceFailedEvent(
        Long orderId,
        String reason
) {
}