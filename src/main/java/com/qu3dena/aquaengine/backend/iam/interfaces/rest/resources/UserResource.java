package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

/**
 * Resource representing a user.
 * <p>
 * Contains the user's unique identifier, username, and role assigned to them.
 * </p>
 *
 * @param id       the unique identifier of the user
 * @param username the username of the user
 * @param role     the role assigned to the user
 */
public record UserResource(Long id, String username, String role) {
}