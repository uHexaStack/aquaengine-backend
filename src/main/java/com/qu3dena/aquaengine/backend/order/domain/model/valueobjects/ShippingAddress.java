package com.qu3dena.aquaengine.backend.order.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ShippingAddress(String street, String city, String postalCode, String country) {

    public ShippingAddress {
        if (street == null || street.isBlank())
            throw new IllegalArgumentException("Street cannot be null or blank");

        if (city == null || city.isBlank())
            throw new IllegalArgumentException("City cannot be null or blank");

        if (postalCode == null || postalCode.isBlank())
            throw new IllegalArgumentException("Postal code cannot be null or blank");

        if (country == null || country.isBlank())
            throw new IllegalArgumentException("Country cannot be null or blank");
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", street, city, postalCode, country);
    }
}
