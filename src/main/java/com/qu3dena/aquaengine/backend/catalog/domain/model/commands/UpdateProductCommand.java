package com.qu3dena.aquaengine.backend.catalog.domain.model.commands;

public record UpdateProductCommand(Long id, String name, String description) {
        public UpdateProductCommand {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Product ID must be a positive number");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("Description cannot be null or blank");
            }
        }
    }