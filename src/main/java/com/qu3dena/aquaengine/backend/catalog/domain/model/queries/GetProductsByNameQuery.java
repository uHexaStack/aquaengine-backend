package com.qu3dena.aquaengine.backend.catalog.domain.model.queries;

public record GetProductsByNameQuery(String name) {
    public GetProductsByNameQuery {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }
    }
}
