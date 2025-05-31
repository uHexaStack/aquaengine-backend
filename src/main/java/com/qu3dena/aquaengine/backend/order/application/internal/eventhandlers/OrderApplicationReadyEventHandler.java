package com.qu3dena.aquaengine.backend.order.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.SeedOrderStatusesCommand;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderStatusCommandService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderApplicationReadyEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplicationReadyEventHandler.class);
    private final OrderStatusCommandService orderStatusCommandService;

    public OrderApplicationReadyEventHandler(OrderStatusCommandService orderStatusCommandService) {
        this.orderStatusCommandService = orderStatusCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        LOGGER.info("Application is ready. Seeding order statuses...");
        orderStatusCommandService.handle(new SeedOrderStatusesCommand());
        LOGGER.info("Order statuses seeded successfully.");
    }
}
