package com.qu3dena.aquaengine.backend.payment.domain.model.events;

import java.math.BigDecimal;

/**
 * Represents an event triggered when a payment is processed successfully.
 *
 * @param orderId  the unique identifier of the order associated with the payment
 * @param amount   the processed payment amount
 * @param currency the currency of the payment
 */
public record PaymentProcessedEvent(
        Long userId,
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        String currency
) {
}