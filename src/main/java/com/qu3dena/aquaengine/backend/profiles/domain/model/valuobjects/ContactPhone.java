package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing a contact phone number.
 * <p>
 * Ensures the phone number is well-formed, not null, and matches an international format.
 * </p>
 */
@Embeddable
public record ContactPhone(String number) {

    /**
     * Regular expression pattern for validating phone numbers.
     * Accepts optional leading '+' and 9 to 15 digits.
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{9,15}$");

    /**
     * Constructs a {@code ContactPhone} instance and validates the phone number format.
     *
     * @param number the contact phone number
     * @throws NullPointerException     if the phone number is null
     * @throws IllegalArgumentException if the phone number format is invalid
     */
    public ContactPhone {
        Objects.requireNonNull(number, "Contact phone number cannot be null");

        String trimmed = number.strip();

        if (!PHONE_PATTERN.matcher(trimmed).matches())
            throw new IllegalArgumentException("Invalid contact phone number format: " + trimmed);
    }
}