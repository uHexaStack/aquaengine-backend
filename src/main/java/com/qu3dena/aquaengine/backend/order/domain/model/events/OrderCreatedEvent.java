package com.qu3dena.aquaengine.backend.order.domain.model.events;

public record OrderCreatedEvent(Long orderId, Long userId) {
}
