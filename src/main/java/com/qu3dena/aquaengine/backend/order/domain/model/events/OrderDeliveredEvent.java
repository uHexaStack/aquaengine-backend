package com.qu3dena.aquaengine.backend.order.domain.model.events;

public record OrderDeliveredEvent(Long orderId, Long userId) {
}
