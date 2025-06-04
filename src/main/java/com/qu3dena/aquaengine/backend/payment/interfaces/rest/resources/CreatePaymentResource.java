package com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Resource record for creating a payment.
 *
 * @param orderId  the unique identifier of the order
 * @param amount   the amount of the payment
 * @param currency the currency used for the payment
 * @param method   the method used for the payment (e.g., CREDIT_CARD)
 */
public record CreatePaymentResource(
        Long orderId,
        BigDecimal amount,
        String currency,
        String method
) {
}