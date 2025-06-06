package com.qu3dena.aquaengine.backend.iam.domain.model.commands;


import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;

public record SignUpCommand(
        String username,
        String password,
        Role role,
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
    public SignUpCommand {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or blank");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null or blank");
    }
}
