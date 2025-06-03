package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

public record CreateInventoryItemResource(
        Long productId,
        int initialQuantity
) {
}
