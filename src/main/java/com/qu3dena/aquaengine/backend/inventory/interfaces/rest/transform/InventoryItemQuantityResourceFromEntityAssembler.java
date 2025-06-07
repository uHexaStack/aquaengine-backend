package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources.InventoryItemQuantityResource;

public class InventoryItemQuantityResourceFromEntityAssembler {
    public static InventoryItemQuantityResource toResourceFromEntity(InventoryItemAggregate entity) {
        return new InventoryItemQuantityResource(
                entity.getUserId(),
                entity.getName(),
                entity.getQuantityOnHand()
        );
    }
}
