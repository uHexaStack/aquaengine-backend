package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record ReserveInventoryCommand(Long productId, int quantityToReserve) {

    public ReserveInventoryCommand {
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (quantityToReserve <= 0) {
            throw new IllegalArgumentException("Quantity to reserve must be > 0");
        }
    }
}
