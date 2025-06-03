package com.qu3dena.aquaengine.backend.inventory.domain.model.queries;

public record GetInventoryByProductIdQuery(Long productId) {
    public GetInventoryByProductIdQuery {
        if (productId == null ) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}
