package com.qu3dena.aquaengine.backend.order.domain.services;

import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetAllOrderStatusTypeQuery;

import java.util.Set;

public interface OrderStatusQueryService {

    Set<OrderStatus> handle(GetAllOrderStatusTypeQuery query);
}
