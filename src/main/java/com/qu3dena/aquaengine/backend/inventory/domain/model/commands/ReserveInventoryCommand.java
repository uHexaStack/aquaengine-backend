package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record ReserveInventoryCommand(Long itemId, int quantityToReserve) {

    public ReserveInventoryCommand {
        if (itemId == null) {
            throw new IllegalArgumentException("ItemId cannot be null");
        }
        if (quantityToReserve <= 0) {
            throw new IllegalArgumentException("Quantity to reserve must be greater than zero");
        }
    }
}
