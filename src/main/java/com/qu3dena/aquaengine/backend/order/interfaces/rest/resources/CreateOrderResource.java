package com.qu3dena.aquaengine.backend.order.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderResource(
        Long userId,
        ShippingAddressResource shippingAddress,
        List<CreateOrderLineResource> lines
) {
    public record ShippingAddressResource(
            String street,
            String city,
            String state,
            String postalCode,
            String country
    ) {}

    public record CreateOrderLineResource(
            Long productId,
            Integer quantity,
            BigDecimal unitPrice,
            String currency
    ) {}
}