package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Assembler for converting a set of {@link Role} entities into a set of role names (as strings).
 */
public class RoleStringSetFromEntitySetAssembler {

    /**
     * Converts a set of {@link Role} entities into a set of role names (strings).
     *
     * @param entity the set of {@link Role} entities to convert
     * @return a set of role names (strings) corresponding to the provided entities
     */
    public static Set<String> toResourceSetFromEntitySet(Set<Role> entity) {
        return entity.stream()
                .map(Role::getStringName)
                .collect(Collectors.toSet());
    }
}