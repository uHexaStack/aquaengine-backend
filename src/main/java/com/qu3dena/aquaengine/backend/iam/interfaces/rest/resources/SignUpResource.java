package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

import java.util.Set;

/**
 * Resource representing the data required for user sign-up.
 * <p>
 * Contains the username, password, and the set of roles assigned to the new user.
 * </p>
 *
 * @param username the username of the user to be registered
 * @param password the password of the user to be registered
 * @param roles    the set of roles assigned to the user
 */
public record SignUpResource(String username, String password, Set<String> roles) {
}