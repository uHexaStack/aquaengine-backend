package com.qu3dena.aquaengine.backend.order.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.OrderResource;

public class OrderResourceFromEntityAssembler {

    public static OrderResource toResourceFromEntity(OrderAggregate entity) {
        return new OrderResource(
                entity.getId(),
                entity.getUserId(),
                entity.getStatus().getStringStatus(),
                entity.getShippingAddress().toString(),
                entity.getTotal().amount()
        );
    }
}
