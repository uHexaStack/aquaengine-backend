package com.qu3dena.aquaengine.backend.iam.domain.model.valueobjects;

/**
 * Enumeration of the available user roles in the system.
 * <p>
 * Defines the different roles that can be assigned to users for access control and authorization.
 * </p>
 */
public enum Roles {
    /**
     * Standard user role with basic permissions.
     */
    ROLE_USER,
    /**
     * Administrator role with elevated permissions.
     */
    ROLE_ADMIN,
    /**
     * Role for users who provide machinery services.
     */
    ROLE_MACHINERY_PROVIDER,
    /**
     * Role for users representing fishing companies.
     */
    ROLE_FISHING_COMPANY
}