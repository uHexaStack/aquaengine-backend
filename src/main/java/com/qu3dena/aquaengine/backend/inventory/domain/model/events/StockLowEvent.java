package com.qu3dena.aquaengine.backend.inventory.domain.model.events;

public record StockLowEvent(
        Long itemId,
        Long productId,
        int availableQuantity
) {
}
