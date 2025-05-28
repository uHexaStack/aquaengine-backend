package com.qu3dena.aquaengine.backend.iam.domain.model.events;

public record UserRegisteredEvent(Long userId, String username) {
}
