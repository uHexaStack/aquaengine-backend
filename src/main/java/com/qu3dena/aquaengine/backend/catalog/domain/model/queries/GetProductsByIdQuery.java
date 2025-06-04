package com.qu3dena.aquaengine.backend.catalog.domain.model.queries;

public record GetProductsByIdQuery(Long id) {
    public GetProductsByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}
