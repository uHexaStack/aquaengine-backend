package com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record CompanyInfo(String name, String street, String city, @Embedded PostalCode postalCode, String country) {

    public CompanyInfo {
        if (name == null || street == null || city == null || postalCode == null || country == null)
            throw new IllegalArgumentException("All fields must be provided");
    }

    public String getCompanyInfo() {
        return String.format("%s, %s, %s, %s, %s", name, street, city, postalCode.toString(), country);
    }
}