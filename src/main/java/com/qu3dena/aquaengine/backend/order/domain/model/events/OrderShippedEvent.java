package com.qu3dena.aquaengine.backend.order.domain.model.events;

public record OrderShippedEvent(Long orderId, Long userId) {
}
