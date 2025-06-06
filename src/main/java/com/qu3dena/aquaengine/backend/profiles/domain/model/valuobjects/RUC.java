package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RUC(String value) {

    public RUC {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("RUC cannot be null or blank");
        }

        if (!value.matches("\\d{11}")) {
            throw new IllegalArgumentException("RUC must be exactly 11 digits");
        }
    }
}
