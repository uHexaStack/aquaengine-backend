package com.qu3dena.aquaengine.backend.profiles.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates.ProfileAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for managing {@link ProfileAggregate} entities.
 * <p>
 * Provides CRUD operations and custom queries for user profiles.
 * </p>
 */
@Repository
public interface ProfileRepository extends JpaRepository<ProfileAggregate, Long> {

    /**
     * Retrieves a profile by its associated user ID.
     *
     * @param userId the unique identifier of the user.
     * @return an {@link Optional} containing the profile if found, or empty otherwise.
     */
    Optional<ProfileAggregate> findByUserId(Long userId);
}