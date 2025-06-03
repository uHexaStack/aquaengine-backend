package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record ReleaseInventoryCommand(Long productId, int quantityToRelease) {

    public ReleaseInventoryCommand {
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (quantityToRelease <= 0) {
            throw new IllegalArgumentException("Quantity to release must be > 0");
        }
    }
}
