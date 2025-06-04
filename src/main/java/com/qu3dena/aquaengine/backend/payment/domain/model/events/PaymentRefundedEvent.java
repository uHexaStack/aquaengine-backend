package com.qu3dena.aquaengine.backend.payment.domain.model.events;

/**
 * Represents an event triggered when a payment is refunded.
 *
 * @param paymentId the unique identifier of the refunded payment
 * @param orderId the unique identifier of the order associated with the refund
 */
public record PaymentRefundedEvent(Long paymentId, Long orderId) {
}