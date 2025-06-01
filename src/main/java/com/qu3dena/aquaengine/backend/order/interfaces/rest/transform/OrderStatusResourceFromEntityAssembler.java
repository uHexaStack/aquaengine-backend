package com.qu3dena.aquaengine.backend.order.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.interfaces.rest.resources.OrderStatusResource;

public class OrderStatusResourceFromEntityAssembler {

    public static OrderStatusResource toResourceFromEntity(OrderStatus entity) {
        return new OrderStatusResource(
                entity.getId(),
                entity.getStringStatus()
        );
    }
}
