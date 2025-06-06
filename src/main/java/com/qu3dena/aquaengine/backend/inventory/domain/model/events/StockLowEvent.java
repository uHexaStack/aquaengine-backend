package com.qu3dena.aquaengine.backend.inventory.domain.model.events;

public record StockLowEvent(
        Long itemId,
        String name,
        int availableQuantity
) {
}
