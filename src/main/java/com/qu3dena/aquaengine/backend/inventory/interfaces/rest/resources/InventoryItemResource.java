package com.qu3dena.aquaengine.backend.inventory.interfaces.rest.resources;

import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;

public record InventoryItemResource(
        Long id,
        String name,
        Money price,
        int quantityOnHand,
        int threshold
) { }
