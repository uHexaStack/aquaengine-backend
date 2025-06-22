package com.qu3dena.aquaengine.backend.iam.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.qu3dena.aquaengine.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignInCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignUpCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.events.UserRegisteredEvent;
import com.qu3dena.aquaengine.backend.iam.domain.services.UserCommandService;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the user command service.
 * <p>
 * This service handles user registration and authentication, ensuring role validation,
 * secure password storage, and authentication token generation. The method documentation below
 * utilizes the {@inheritDoc} tag to inherit descriptions from the interface.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final TokenService tokenService;
    private final HashingService hashingService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher events;

    /**
     * Creates a new instance of {@code UserCommandServiceImpl} with the required dependencies.
     *
     * @param userRepository repository for user operations
     * @param roleRepository repository for role operations
     * @param tokenService   service for token generation
     * @param hashingService service for password validation and hashing
     * @param events         publisher for application events
     */
    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TokenService tokenService, HashingService hashingService, ApplicationEventPublisher events) {
        this.events = events;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Optional<UserAggregate> handle(SignUpCommand command) {

        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        var encoded = hashingService.encode(command.password());

        var roleEntity = roleRepository.findByName(command.role().getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        var user = UserAggregate.create(command.username(), encoded, roleEntity);

        var saved = userRepository.save(user);

        var event = new UserRegisteredEvent(
                saved.getId(),
                command.firstName(),
                command.lastName(),
                command.ruc(),
                command.contactEmail(),
                command.contactPhone(),
                command.companyName(),
                command.companyStreet(),
                command.companyCity(),
                command.postalCode(),
                command.companyNumber(),
                command.companyCountry()
        );

        events.publishEvent(event);

        return Optional.of(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ImmutablePair<UserAggregate, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());

        if (user.isEmpty())
            throw new RuntimeException("User not found");

        var existingUser = user.get();

        if (!hashingService.matches(command.password(), existingUser.getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(existingUser.getId().toString());

        return Optional.of(ImmutablePair.of(existingUser, token));
    }
}