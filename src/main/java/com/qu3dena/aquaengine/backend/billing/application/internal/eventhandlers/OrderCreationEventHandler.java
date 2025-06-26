package com.qu3dena.aquaengine.backend.billing.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceCommandService;
import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreationEventHandler {

    private final InvoiceCommandService commandService;

    public OrderCreationEventHandler(InvoiceCommandService commandService) {
        this.commandService = commandService;
    }

    @EventListener
    public void handle(OrderCreatedEvent event) {
        IssueInvoiceCommand command = new IssueInvoiceCommand(
                event.orderId(),
                event.amount(),
                event.currency()
        );
        commandService.handle(command);

    }
}
