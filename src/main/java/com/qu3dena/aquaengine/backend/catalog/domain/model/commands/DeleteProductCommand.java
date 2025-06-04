package com.qu3dena.aquaengine.backend.catalog.domain.model.commands;

public record DeleteProductCommand(Long id) {
    public DeleteProductCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }
}
