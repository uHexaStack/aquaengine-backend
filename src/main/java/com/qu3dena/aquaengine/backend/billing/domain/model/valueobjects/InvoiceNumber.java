package com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects;

import java.util.UUID;

public record InvoiceNumber(String value) {

    public InvoiceNumber {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Invoice number cannot be null or blank");
    }

    public static InvoiceNumber generate() {
        return new InvoiceNumber("INV-" + UUID.randomUUID());
    }
}
