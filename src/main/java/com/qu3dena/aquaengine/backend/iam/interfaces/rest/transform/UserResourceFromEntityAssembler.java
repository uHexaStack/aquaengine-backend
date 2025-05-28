package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler for converting a {@link UserAggregate} entity into a {@link UserResource} representation.
 */
public class UserResourceFromEntityAssembler {

    /**
     * Converts a {@link UserAggregate} entity into a {@link UserResource}.
     *
     * @param entity the user aggregate entity to convert
     * @return the corresponding {@link UserResource}
     */
    public static UserResource toResourceFromEntity(UserAggregate entity) {
        return new UserResource(
                entity.getId(),
                entity.getUsername(),
                RoleStringSetFromEntitySetAssembler.toResourceSetFromEntitySet(entity.getRoles()));
    }
}