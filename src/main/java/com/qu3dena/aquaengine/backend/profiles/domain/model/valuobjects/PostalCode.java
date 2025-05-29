package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing a postal code.
 * <p>
 * Validates that the postal code is a 5-digit number.
 * </p>
 */
@Embeddable
public record PostalCode(String value) {

    private static final Pattern ZIP5 = Pattern.compile("^\\d{5}$");

    public PostalCode {
        Objects.requireNonNull(value, "The postal code cannot be null");

        String trimmed = value.strip();

        if (!ZIP5.matcher(trimmed).matches())
            throw new IllegalArgumentException(
                    "The postal code must be a 5-digit number, but was: " + trimmed);

        value = trimmed;
    }

    @Override
    public String toString() {
        return value;
    }
}