package com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Resource record representing a payment.
 *
 * @param id              the unique identifier of the payment
 * @param orderId         the unique identifier of the order associated with the payment
 * @param amount          the amount of the payment
 * @param currency        the currency used for the payment
 * @param status          the current status of the payment
 * @param paymentMethod   the method used for the payment (e.g., CREDIT_CARD)
 * @param transactionDate the date and time when the transaction occurred
 */
public record PaymentResource(
        Long id,
        Long orderId,
        BigDecimal amount,
        String currency,
        String status,
        String paymentMethod,
        Instant transactionDate
) {
}