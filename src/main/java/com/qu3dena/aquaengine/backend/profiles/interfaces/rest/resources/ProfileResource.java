package com.qu3dena.aquaengine.backend.profiles.interfaces.rest.resources;

public record ProfileResource(
        Long userId,
        String fullName,
        String ruc,
        String contactEmail,
        String contactPhone,
        String companyInfo
) {
}
