package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record ReleaseInventoryCommand(Long itemId, int quantityToRelease) {

    public ReleaseInventoryCommand {
        if (itemId == null) {
            throw new IllegalArgumentException("ItemId cannot be null");
        }
        if (quantityToRelease <= 0) {
            throw new IllegalArgumentException("Quantity to release must be greater than zero");
        }
    }
}
