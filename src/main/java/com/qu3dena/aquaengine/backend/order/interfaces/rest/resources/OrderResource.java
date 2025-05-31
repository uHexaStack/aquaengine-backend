package com.qu3dena.aquaengine.backend.order.interfaces.rest.resources;

import java.math.BigDecimal;

public record OrderResource(
        Long id,
        Long userId,
        String status,
        String shippingAddress,
        BigDecimal totalAmount
) {
}
