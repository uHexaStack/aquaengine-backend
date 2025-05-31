package com.qu3dena.aquaengine.backend.order.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.SeedOrderStatusesCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.OrderStatusType;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderStatusCommandService;
import com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderStatusCommandServiceImpl implements OrderStatusCommandService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusCommandServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void handle(SeedOrderStatusesCommand command) {
        Arrays.stream(OrderStatusType.values()).forEach(orderStatusType -> {
            if (!orderStatusRepository.existsByName(orderStatusType))
                orderStatusRepository.save(OrderStatus.create(orderStatusType.name()));
        });
    }
}
