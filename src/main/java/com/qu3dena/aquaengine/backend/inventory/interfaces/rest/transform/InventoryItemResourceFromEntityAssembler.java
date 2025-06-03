package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.InventoryItemResource;

/**
 * Assembler to transform an InventoryItemAggregate entity to an InventoryItemResource.
 */
public class InventoryItemResourceFromEntityAssembler {

    /**
     * Converts an InventoryItemAggregate entity to an InventoryItemResource.
     *
     * @param entity the InventoryItemAggregate entity to convert.
     * @return the corresponding InventoryItemResource.
     */
    public static InventoryItemResource toResourceFromEntity(InventoryItemAggregate entity) {
        return new InventoryItemResource(
                entity.getId(),
                entity.getProductId(),
                entity.getAvailableQuantity()
        );
    }
}