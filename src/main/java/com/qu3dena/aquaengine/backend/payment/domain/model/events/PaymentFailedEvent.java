package com.qu3dena.aquaengine.backend.payment.domain.model.events;

/**
 * Represents an event triggered when a payment fails.
 *
 * @param orderId the unique identifier of the order associated with the failed payment
 * @param reason  the reason for the payment failure
 */
public record PaymentFailedEvent(Long orderId, String reason) {
}