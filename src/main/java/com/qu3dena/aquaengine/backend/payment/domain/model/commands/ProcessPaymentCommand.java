package com.qu3dena.aquaengine.backend.payment.domain.model.commands;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Command to process a payment.
 * <p>
 * This record encapsulates the payment processing details including the order identifier, amount,
 * currency, and payment method. It validates the provided values upon construction.
 * </p>
 *
 * @param orderId  the unique identifier for the order; must not be null
 * @param amount   the monetary amount for the payment; must be greater than zero
 * @param currency the currency of the payment; must not be null or blank
 * @param method   the payment method; must not be null or blank
 */
public record ProcessPaymentCommand(
        Long orderId,
        BigDecimal amount,
        String currency,
        String method
) {
    public ProcessPaymentCommand {
        Objects.requireNonNull(orderId, "OrderId cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.signum() <= 0)
            throw new IllegalArgumentException("Amount must be > 0");

        if (currency == null || currency.isBlank())
            throw new IllegalArgumentException("Currency cannot be null or blank");

        if (method == null || method.isBlank())
            throw new IllegalArgumentException("Payment method cannot be null or blank");
    }
}