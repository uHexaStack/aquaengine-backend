package com.qu3dena.aquaengine.backend.iam.application.internal.queryservices;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetAllUsersQuery;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetUserByUsernameQuery;
import com.qu3dena.aquaengine.backend.iam.domain.services.UserQueryService;
import com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the user query service.
 * <p>
 * Provides methods to retrieve user information from the database,
 * including fetching all users, searching by user ID, and searching by username.
 * </p>
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    /**
     * Creates a new instance of {@code UserQueryServiceImpl} with the required user repository.
     *
     * @param userRepository repository for user persistence operations
     */
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all users from the database.
     * </p>
     *
     * @param query query to get all users
     * @return a list of all user aggregates
     */
    @Override
    public List<UserAggregate> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a user by their unique identifier.
     * </p>
     *
     * @param query query to get a user by ID
     * @return an {@code Optional} containing the user aggregate if found, or empty otherwise
     */
    @Override
    public Optional<UserAggregate> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a user by their username.
     * </p>
     *
     * @param query query to get a user by username
     * @return an {@code Optional} containing the user aggregate if found, or empty otherwise
     */
    @Override
    public Optional<UserAggregate> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}