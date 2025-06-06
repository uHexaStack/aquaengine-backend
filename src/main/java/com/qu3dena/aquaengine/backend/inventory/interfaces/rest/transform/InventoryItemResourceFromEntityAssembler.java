package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.InventoryItemResource;

public class InventoryItemResourceFromEntityAssembler {

    public static InventoryItemResource toResourceFromEntity(InventoryItemAggregate entity) {
        return new InventoryItemResource(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantityOnHand(),
                entity.getThreshold()
        );
    }
}
