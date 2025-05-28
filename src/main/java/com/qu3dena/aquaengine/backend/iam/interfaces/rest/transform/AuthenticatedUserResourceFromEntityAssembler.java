package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler for converting a {@link UserAggregate} entity and authentication token
 * into an {@link AuthenticatedUserResource} representation.
 */
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Converts a {@link UserAggregate} entity and a JWT token into an {@link AuthenticatedUserResource}.
     *
     * @param entity the user aggregate entity to convert
     * @param token  the JWT authentication token
     * @return the corresponding {@link AuthenticatedUserResource}
     */
    public static AuthenticatedUserResource toResourceFromEntity(UserAggregate entity, String token) {
        return new AuthenticatedUserResource(
                entity.getId(),
                entity.getUsername(),
                token
        );
    }
}