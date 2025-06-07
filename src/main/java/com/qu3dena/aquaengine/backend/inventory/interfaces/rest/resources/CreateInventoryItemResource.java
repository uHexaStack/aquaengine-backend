package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;

public record CreateInventoryItemResource(
        Long userId,
        String name,
        Money price,
        int initialQuantity,
        Integer threshold
) {
}