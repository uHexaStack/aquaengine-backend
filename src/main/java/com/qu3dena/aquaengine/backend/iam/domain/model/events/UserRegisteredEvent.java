package com.qu3dena.aquaengine.backend.iam.domain.model.events;

public record UserRegisteredEvent(
        Long userId,
        String firstName,
        String lastName,
        String ruc,
        String contactEmail,
        String contactPhone,
        String companyName,
        String companyStreet,
        String companyCity,
        String postalCode,
        String companyNumber,
        String companyCountry
) {
}
