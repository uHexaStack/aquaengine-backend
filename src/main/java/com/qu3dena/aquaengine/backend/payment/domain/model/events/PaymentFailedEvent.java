package com.qu3dena.aquaengine.backend.payment.domain.model.events;

/**
 * Represents an event triggered when a payment fails.
 *
 * @param userId  the unique identifier of the user associated with the failed payment
 * @param orderId the unique identifier of the order associated with the failed payment
 * @param reason  the reason for the payment failure
 */
public record PaymentFailedEvent(Long userId, Long orderId, String reason) {
}