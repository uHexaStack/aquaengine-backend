package com.qu3dena.aquaengine.backend.order.domain.model.commands;

import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.ShippingAddress;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(
        Long userId,
        ShippingAddress shippingAddress,
        List<Line> lines
) {
    public record Line(
            Long productId,
            Integer quantity,
            BigDecimal unitPrice,
            String currency
    ) {
        public Line {
            if (quantity <= 0)
                throw new IllegalArgumentException("Quantity must be > 0");

            if (unitPrice == null || unitPrice.signum() < 0)
                throw new IllegalArgumentException("Unit price must be non-negative");

            if (currency == null || currency.isBlank())
                throw new IllegalArgumentException("Currency is required");
        }
    }
}
