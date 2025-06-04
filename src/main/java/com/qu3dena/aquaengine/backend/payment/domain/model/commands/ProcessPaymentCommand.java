package com.qu3dena.aquaengine.backend.payment.domain.model.commands;

import java.math.BigDecimal;
import java.util.Objects;

public record ProcessPaymentCommand(
        Long orderId,
        BigDecimal amount,
        String currency,
        String method
) {
    public ProcessPaymentCommand {
        Objects
                .requireNonNull(orderId, "OrderId cannot be null");
        Objects
                .requireNonNull(amount, "Amount cannot be null");

        if (amount.signum() <= 0)
            throw new IllegalArgumentException("Amount must be > 0");

        if (currency == null || currency.isBlank())
            throw new IllegalArgumentException("Currency cannot be null or blank");

        if (method == null || method.isBlank())
            throw new IllegalArgumentException("Payment method cannot be null or blank");
    }
}
