package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

/**
 * Resource representing an authenticated user.
 * <p>
 *     Contains the user's unique identifier, username, and authentication token.
 * </p>
 *
 * @param id      the unique identifier of the user
 * @param username the username of the authenticated user
 * @param token    the authentication token issued to the user
 */
public record AuthenticatedUserResource(Long id, String username, String token) {
}