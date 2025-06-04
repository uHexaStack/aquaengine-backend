package com.qu3dena.aquaengine.backend.payment.domain.model.queries;

import java.util.Objects;

public record GetPaymentsByOrderIdQuery(Long orderId) {
    public GetPaymentsByOrderIdQuery {
        Objects.requireNonNull(orderId, "OrderId cannot be null");
    }
}
