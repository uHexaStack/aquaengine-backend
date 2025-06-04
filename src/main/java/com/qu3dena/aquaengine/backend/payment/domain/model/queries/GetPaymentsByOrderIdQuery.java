package com.qu3dena.aquaengine.backend.payment.domain.model.queries;

import java.util.Objects;

/**
 * Query to retrieve payments associated with a specific order.
 *
 * @param orderId the unique identifier of the order; must not be null
 */
public record GetPaymentsByOrderIdQuery(Long orderId) {
    public GetPaymentsByOrderIdQuery {
        Objects.requireNonNull(orderId, "OrderId cannot be null");
    }
}