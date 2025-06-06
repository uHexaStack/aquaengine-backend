package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;

public record CreateInventoryItemResource(
        String name,
        Money price,
        int initialQuantity,
        Integer threshold
) {
}