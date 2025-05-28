package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignInCommand;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler for converting a {@link SignInResource} into a {@link SignInCommand}.
 */
public class SignInCommandFromResourceAssembler {

    /**
     * Converts a {@link SignInResource} into a {@link SignInCommand}.
     *
     * @param resource the sign-in resource containing user credentials
     * @return a {@link SignInCommand} with the provided username and password
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.username(),
                resource.password());
    }
}