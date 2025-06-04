package com.qu3dena.aquaengine.backend.catalog.domain.model.commands;

public record CreateProductCommand(Long id,String name, String description) {

    public CreateProductCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}
