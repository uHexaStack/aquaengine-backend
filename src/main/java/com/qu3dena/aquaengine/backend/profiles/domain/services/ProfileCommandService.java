package com.qu3dena.aquaengine.backend.profiles.domain.services;

import com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates.ProfileAggregate;
import com.qu3dena.aquaengine.backend.profiles.domain.model.commands.CreateProfileCommand;

import java.util.Optional;

/**
 * Command service for managing user profiles.
 * <p>
 * Defines operations related to profile creation.
 * </p>
 */
public interface ProfileCommandService {

    /**
     * Handles the command to create a new user profile.
     *
     * @param command Command containing the data required to create the profile.
     * @return An {@link Optional} containing the created profile if the operation was successful, or empty otherwise.
     */
    Optional<ProfileAggregate> handle(CreateProfileCommand command);
}