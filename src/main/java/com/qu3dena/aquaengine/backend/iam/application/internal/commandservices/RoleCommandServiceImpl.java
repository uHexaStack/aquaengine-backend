package com.qu3dena.aquaengine.backend.iam.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SeedRolesCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;
import com.qu3dena.aquaengine.backend.iam.domain.model.valueobjects.Roles;
import com.qu3dena.aquaengine.backend.iam.domain.services.RoleCommandService;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of the role command service.
 * <p>
 * Allows initializing the predefined roles in the system,
 * ensuring they exist in the database.
 * </p>
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    /**
     * Creates a new instance of {@code RoleCommandServiceImpl} with the required role repository.
     *
     * @param roleRepository repository for role persistence operations
     */
    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initializes the roles defined in the {@link Roles} enum if they do not exist in the database.
     * </p>
     *
     * @param command command to seed the roles
     */
    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role))
                roleRepository.save(Role.create(role.name()));
        });
    }
}