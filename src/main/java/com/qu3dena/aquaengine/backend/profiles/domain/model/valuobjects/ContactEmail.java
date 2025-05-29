package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing a contact email address.
 * <p>
 * Ensures the email address is well-formed and not null.
 * </p>
 */
@Embeddable
public record ContactEmail(String value) {

    /**
     * Regular expression pattern for validating email addresses.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    /**
     * Constructs a {@code ContactEmail} instance and validates the email format.
     *
     * @param value the email address value
     * @throws NullPointerException     if the email is null
     * @throws IllegalArgumentException if the email format is invalid
     */
    public ContactEmail {
        Objects.requireNonNull(value, "ContactEmail cannot be null");

        if (!EMAIL_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("Invalid contactEmail format: " + value);
    }
}