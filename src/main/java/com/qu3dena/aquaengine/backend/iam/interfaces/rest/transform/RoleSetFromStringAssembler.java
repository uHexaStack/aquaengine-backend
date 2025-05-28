package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Assembler for converting a set of role names (as strings) into a set of {@link Role} entities.
 */
public class RoleSetFromStringAssembler {

    /**
     * Converts a set of role names (strings) into a set of {@link Role} entities.
     *
     * @param resourceList the set of role names to convert
     * @return a set of {@link Role} entities corresponding to the provided names,
     * or an empty set if the input is {@code null}
     */
    public static Set<Role> toRoleSetFromStringSet(Set<String> resourceList) {
        return Objects.nonNull(resourceList)
                ? resourceList.stream().map(Role::toRoleFromName).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}