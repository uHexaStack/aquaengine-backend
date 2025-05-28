package com.qu3dena.aquaengine.backend.iam.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.qu3dena.aquaengine.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignInCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignUpCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.valueobjects.Roles;
import com.qu3dena.aquaengine.backend.iam.domain.services.UserCommandService;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the user command service.
 * <p>
 * Manages user registration and authentication, including role validation,
 * secure password storage, and authentication token generation.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final HashingService hashingService;

    /**
     * Creates a new instance of {@code UserCommandServiceImpl} with the required dependencies.
     *
     * @param userRepository repository for user operations
     * @param roleRepository repository for role operations
     * @param tokenService   service for token generation
     * @param hashingService service for password validation and hashing
     */
    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TokenService tokenService, HashingService hashingService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.hashingService = hashingService;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Registers a new user, validating username uniqueness and role existence.
     * Stores the password and associated roles, and persists the user in the database.
     * </p>
     *
     * @param command user registration command
     * @return an {@code Optional} with the registered user if the operation was successful
     * @throws RuntimeException if the username already exists or any role is not found
     */
    @Override
    public Optional<UserAggregate> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found"))).collect(Collectors.toSet());

        var encodedPassword = hashingService.encode(command.password());

        var user = UserAggregate.create(command.username(), encodedPassword, roles);

        userRepository.save(user);

        return userRepository.findByUsername(command.username());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Authenticates a user by verifying existence and password validity.
     * If successful, generates and returns an authentication token.
     * </p>
     *
     * @param command sign-in command
     * @return an {@code Optional} with the user and token pair if authentication is successful
     * @throws RuntimeException if the user does not exist or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<UserAggregate, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());

        if (user.isEmpty())
            throw new RuntimeException("User not found");

        var existingUser = user.get();

        if (!hashingService.matches(command.password(), existingUser.getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(existingUser.getUsername());

        return Optional.of(ImmutablePair.of(existingUser, token));
    }
}