package com.qu3dena.aquaengine.backend.payment.domain.model.events;

/**
 * Represents an event triggered when a payment is processed successfully.
 *
 * @param paymentId the unique identifier of the processed payment
 * @param orderId   the unique identifier of the order associated with the payment
 */
public record PaymentProcessedEvent(Long paymentId, Long orderId) {
}