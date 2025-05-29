package com.qu3dena.aquaengine.backend.profiles.interfaces.acl;

/**
 * Facade interface for profile context operations.
 */
public interface ProfileContextFacade {

    /**
     * Creates a new user profile with the provided information.
     *
     * @param userId         the user ID
     * @param firstName      the user's first name
     * @param lastName       the user's last name
     * @param contactEmail   the user's contact email
     * @param contactPhone   the user's contact phone
     * @param companyName    the company name
     * @param companyStreet  the company street
     * @param companyCity    the company city
     * @param postalCode     the postal code
     * @param companyCountry the company country
     */
    void createProfile(
            Long userId,
            String firstName,
            String lastName,
            String contactEmail,
            String contactPhone,
            String companyName,
            String companyStreet,
            String companyCity,
            String postalCode,
            String companyCountry
    );
}