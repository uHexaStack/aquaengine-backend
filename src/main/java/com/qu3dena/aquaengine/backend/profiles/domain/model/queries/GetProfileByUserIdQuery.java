package com.qu3dena.aquaengine.backend.profiles.domain.model.queries;

/**
 * Query object for retrieving a user profile by user ID.
 * <p>
 * Encapsulates the user ID for which the profile is requested.
 * </p>
 *
 * @param userId Unique identifier of the user whose profile is to be retrieved.
 */
public record GetProfileByUserIdQuery(Long userId) {
}