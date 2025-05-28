package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

import java.util.Set;

/**
 * Resource representing a user.
 * <p>
 * Contains the user's unique identifier, username, and the set of roles assigned to the user.
 * </p>
 *
 * @param id       the unique identifier of the user
 * @param username the username of the user
 * @param roles    the set of roles assigned to the user
 */
public record UserResource(Long id, String username, Set<String> roles) {
}