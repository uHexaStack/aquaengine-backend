package com.qu3dena.aquaengine.backend.iam.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignUpCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;
import com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembler for converting a {@link SignUpResource} into a {@link SignUpCommand}.
 *
 * <p>
 * This class provides the method to convert the data from a REST resource object
 * into a command object used within the application.
 * </p>
 */
public class SignUpCommandFromResourceAssembler {

    /**
     * Converts a {@link SignUpResource} into a {@link SignUpCommand}.
     *
     * @param resource the sign-up resource containing user registration data
     * @return a new {@link SignUpCommand} constructed from the provided resource
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roleEntity = Role.toRoleFromName(resource.role());

        System.out.println("Role Entity: " + roleEntity);

        return new SignUpCommand(
                resource.username(),
                resource.password(),
                roleEntity,
                resource.firstName(),
                resource.lastName(),
                resource.contactEmail(),
                resource.contactPhone(),
                resource.companyName(),
                resource.companyStreet(),
                resource.companyCity(),
                resource.postalCode(),
                resource.companyNumber(),
                resource.companyCountry()
        );
    }
}