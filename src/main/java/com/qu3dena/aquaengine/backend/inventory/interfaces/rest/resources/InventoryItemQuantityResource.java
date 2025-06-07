package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

public record InventoryItemQuantityResource(
        Long userId,
        String name,
        int quantityOnHand
) {
}
