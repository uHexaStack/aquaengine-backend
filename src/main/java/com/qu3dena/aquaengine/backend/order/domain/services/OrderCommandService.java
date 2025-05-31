package com.qu3dena.aquaengine.backend.order.domain.services;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.*;

import java.util.Optional;

public interface OrderCommandService {

    Optional<OrderAggregate> handle(CreateOrderCommand command);

    void handle(ConfirmOrderCommand command);

    void handle(CancelOrderCommand command);

    void handle(ShipOrderCommand command);

    void handle(DeliverOrderCommand command);
}
