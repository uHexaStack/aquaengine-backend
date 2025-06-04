package com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects;

import java.util.Objects;

/**
 * Represents a payment method.
 * <p>
 * This record validates that the payment method is non\-null and non\-blank.
 *
 * @param method the payment method
 */
public record PaymentMethod(String method) {
    public PaymentMethod {
        Objects.requireNonNull(method, "Payment method cannot be null");

        if (method.isBlank())
            throw new IllegalArgumentException("Payment method cannot be blank");
    }
}