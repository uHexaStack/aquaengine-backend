package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record AdjustInventoryCommand(Long itemId, int adjustBy) {

    public AdjustInventoryCommand {
        if (itemId == null) {
            throw new IllegalArgumentException("ItemId cannot be null");
        }
    }
}
