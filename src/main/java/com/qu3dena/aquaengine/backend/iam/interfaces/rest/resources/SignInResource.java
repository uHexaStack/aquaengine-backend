package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

/**
 * Resource representing the credentials required for user sign-in.
 * <p>
 *     Contains the username and password provided by the user for authentication.
 * </p>
 *
 * @param username the username of the user attempting to sign in
 * @param password the password of the user attempting to sign in
 */
public record SignInResource(String username, String password) {
}