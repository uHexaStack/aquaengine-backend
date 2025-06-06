package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

public record InventoryItemQuantityResource(
        String name,
        int availableQuantity
) {
}
