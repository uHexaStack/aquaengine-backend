package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler for converting a {@link Role} entity into a {@link RoleResource} representation.
 */
public class RoleResourceFromEntityAssembler {

    /**
     * Converts a {@link Role} entity into a {@link RoleResource}.
     *
     * @param entity the role entity to convert
     * @return the corresponding {@link RoleResource}
     */
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(
                entity.getId(),
                entity.getStringName());
    }
}