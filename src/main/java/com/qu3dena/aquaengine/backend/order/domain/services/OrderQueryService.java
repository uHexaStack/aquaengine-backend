package com.qu3dena.aquaengine.backend.order.domain.services;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrderByIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrdersByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {
    Optional<OrderAggregate> handle(GetOrderByIdQuery query);

    List<OrderAggregate> handle(GetOrdersByUserIdQuery query);
}
