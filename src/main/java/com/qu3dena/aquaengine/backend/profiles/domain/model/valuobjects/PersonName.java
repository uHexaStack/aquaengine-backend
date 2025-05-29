package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a person's name.
 * <p>
 * Ensures that both first and last names are non-null and non-blank.
 * Provides a method to obtain the full name.
 * </p>
 */
@Embeddable
public record PersonName(String firstName, String lastName) {

    /**
     * Constructs a {@code PersonName} instance and validates the name components.
     *
     * @throws IllegalArgumentException if either firstName or lastName is null or blank
     */
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
    }

    /**
     * Returns the full name by concatenating first and last names.
     *
     * @return the full name as a single string
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}