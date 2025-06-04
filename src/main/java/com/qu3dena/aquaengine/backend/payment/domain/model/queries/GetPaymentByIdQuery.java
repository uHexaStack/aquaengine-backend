package com.qu3dena.aquaengine.backend.payment.domain.model.queries;

import java.util.Objects;

/**
 * Query to retrieve a payment by its unique identifier.
 *
 * @param paymentId the unique identifier of the payment; must not be null
 */
public record GetPaymentByIdQuery(Long paymentId) {
    public GetPaymentByIdQuery {
        Objects.requireNonNull(paymentId, "PaymentId cannot be null");
    }
}