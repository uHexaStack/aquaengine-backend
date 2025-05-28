package com.qu3dena.aquaengine.backend.iam.domain.model.queries;

public record GetUserByUsernameQuery(String username) {
    public GetUserByUsernameQuery {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or blank");
    }
}
