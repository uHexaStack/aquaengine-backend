package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

public record InventoryItemResource(
        Long id,
        Long productId,
        int availableQuantity
) {
}
