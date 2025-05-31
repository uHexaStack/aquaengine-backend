package com.qu3dena.aquaengine.backend.order.domain.model.events;

public record OrderCancelledEvent(Long orderId, Long userId) {
}
