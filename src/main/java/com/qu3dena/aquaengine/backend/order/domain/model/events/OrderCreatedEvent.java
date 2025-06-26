package com.qu3dena.aquaengine.backend.order.domain.model.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long orderId, Long userId, BigDecimal amount, String currency) {
}
