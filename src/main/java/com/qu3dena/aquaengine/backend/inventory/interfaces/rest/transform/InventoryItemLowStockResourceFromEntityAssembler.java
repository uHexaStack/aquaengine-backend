package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.InventoryItemLowStockResource;

/**
 * Assembler for converting an {@code InventoryItemAggregate} entity into an {@code InventoryItemLowStockResource}.
 */
public class InventoryItemLowStockResourceFromEntityAssembler {

    /**
     * Converts the given inventory item aggregate into a low stock resource.
     *
     * @param resource the inventory item aggregate to convert
     * @return the corresponding {@code InventoryItemLowStockResource}
     */
    public static InventoryItemLowStockResource toResourceFromEntity(InventoryItemAggregate resource) {
        return new InventoryItemLowStockResource(
                resource.getName(),
                resource.getQuantityOnHand(),
                resource.getThreshold()
        );
    }
}