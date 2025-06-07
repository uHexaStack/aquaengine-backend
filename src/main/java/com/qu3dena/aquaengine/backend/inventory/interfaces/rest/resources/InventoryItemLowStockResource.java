package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

/**
 * Represents an inventory item with low stock details.
 *
 * @param name              the name of the inventory item
 * @param availableQuantity the available quantity of the item
 * @param threshold         the stock threshold that triggers a low stock alert
 */
public record InventoryItemLowStockResource(
        Long userId,
        String name,
        int availableQuantity,
        int threshold
) {
}
