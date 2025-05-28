package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignUpCommand;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembler for converting a {@link SignUpResource} into a {@link SignUpCommand}.
 */
public class SignUpCommandFromResourceAssembler {

    /**
     * Converts a {@link SignUpResource} into a {@link SignUpCommand}.
     *
     * @param resource the sign-up resource containing user credentials and roles
     * @return a {@link SignUpCommand} with the provided username, password, and roles
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = RoleSetFromStringAssembler.toRoleSetFromStringSet(resource.roles());
        System.out.println("roles: " + roles);
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}