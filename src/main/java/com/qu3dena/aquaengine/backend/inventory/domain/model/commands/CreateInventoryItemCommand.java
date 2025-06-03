package com.qu3dena.aquaengine.backend.inventory.domain.model.commands;

public record CreateInventoryItemCommand(Long productId, int initialQuantity) {

    public CreateInventoryItemCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }

        if (initialQuantity < 0) {
            throw new IllegalArgumentException("Initial quantity must be >= 0.");
        }
    }
}
