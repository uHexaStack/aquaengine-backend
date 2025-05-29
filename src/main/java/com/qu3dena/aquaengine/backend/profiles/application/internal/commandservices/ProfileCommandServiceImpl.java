package com.qu3dena.aquaengine.backend.profiles.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates.ProfileAggregate;
import com.qu3dena.aquaengine.backend.profiles.domain.model.commands.CreateProfileCommand;
import com.qu3dena.aquaengine.backend.profiles.domain.services.ProfileCommandService;
import com.qu3dena.aquaengine.backend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the command service for managing user profiles.
 * <p>
 * Provides operations to create new user profiles.
 * </p>
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    /**
     * Constructor that injects the profile repository.
     *
     * @param profileRepository Profile repository.
     */
    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Handles the command to create a new user profile.
     *
     * @param command Command containing the data required to create the profile.
     * @return An {@link Optional} containing the created profile if the operation was successful, or empty otherwise.
     */
    @Override
    public Optional<ProfileAggregate> handle(CreateProfileCommand command) {
        var profile = ProfileAggregate.create(command);
        profileRepository.save(profile);
        return Optional.of(profile);
    }
}