package com.qu3dena.aquaengine.backend.order.domain.services;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.SeedOrderStatusesCommand;

public interface OrderStatusCommandService {

    void handle(SeedOrderStatusesCommand command);
}
