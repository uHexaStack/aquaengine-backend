package com.qu3dena.aquaengine.backend.iam.interfaces.rest.resources;

/**
 * Resource representing a role.
 * <p>
 * Contains the unique identifier and name of the role.
 * </p>
 *
 * @param id   the unique identifier of the role
 * @param name the name of the role
 */
public record RoleResource(Long id, String name) {
}