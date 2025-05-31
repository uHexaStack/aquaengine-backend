package com.qu3dena.aquaengine.backend.order.domain.model.events;

public record OrderConfirmedEvent(Long orderId, Long userId) {
}
