package com.qu3dena.aquaengine.backend.profiles.domain.services;

import com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates.ProfileAggregate;
import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetAllProfilesQuery;
import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetProfileByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service for retrieving user profiles.
 * <p>
 * Defines operations related to querying profiles by their unique identifier.
 * </p>
 */
public interface ProfileQueryService {

    /**
     * Handles the query to retrieve a user profile by its unique identifier.
     *
     * @param query Query containing the ID of the profile to retrieve.
     * @return An {@link Optional} containing the profile if found, or empty otherwise.
     */
    Optional<ProfileAggregate> handle(GetProfileByUserIdQuery query);

    /**
     * Handles the query to retrieve all user profiles.
     *
     * @param query Query to retrieve all profiles.
     * @return An {@link Optional} containing a list of all profiles if found, or empty otherwise.
     */
    List<ProfileAggregate> handle(GetAllProfilesQuery query);
}