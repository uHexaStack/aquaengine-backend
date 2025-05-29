package com.qu3dena.aquaengine.backend.profiles.interfaces.rest.resources;

public record CreateProfileResource(
        Long userId,
        String firstName,
        String lastName,
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
