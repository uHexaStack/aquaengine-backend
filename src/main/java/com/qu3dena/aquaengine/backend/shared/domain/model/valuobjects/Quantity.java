package com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Quantity(Integer amount) {

    public Quantity {
        if (!isPositive())
            throw new IllegalArgumentException("Amount must be a positive integer.");
    }

    private boolean isPositive() {
        return amount != null && amount > 0;
    }
}
