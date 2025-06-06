package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;

public record CreateInventoryItemCommand(
        String name,
        Money price,
        int quantityOnHand,
        int threshold
) {

    public CreateInventoryItemCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Invalid name");

        if (price == null || price.amount().signum() < 0)
            throw new IllegalArgumentException("Invalid price");

        if (quantityOnHand < 0)
            throw new IllegalArgumentException("Initial quantity cannot be negative");

        if (threshold < 0)
            throw new IllegalArgumentException("Threshold cannot be negative");
    }
}
