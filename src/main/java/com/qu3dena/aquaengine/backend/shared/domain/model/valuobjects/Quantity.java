package com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Quantity(Integer amount) {

    public Quantity {
        if (amount == null || amount <= 0)
            throw new IllegalArgumentException("Amount must be a positive integer.");
    }
}
