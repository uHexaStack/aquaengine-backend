package com.qu3dena.aquaengine.backend.billing.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceCommandService;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentProcessedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler for {@code PaymentProcessedEvent} events.
 * <p>
 * When a {@code PaymentProcessedEvent} is received, this handler creates an {@code IssueInvoiceCommand}
 * and delegates it to the {@code InvoiceCommandService} for processing.
 * </p>
 */
@Service
public class BillingPaymentProcessedEventHandler {

    private final InvoiceCommandService commandService;

    /**
     * Constructs a new {@code PaymentProcessedEventHandler} with the specified invoice command service.
     *
     * @param commandService the service responsible for processing invoice commands
     */
    public BillingPaymentProcessedEventHandler(InvoiceCommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Handles the {@code PaymentProcessedEvent} by initiating the creation of an invoice.
     * <p>
     * This method is automatically invoked when a {@code PaymentProcessedEvent} is published.
     * It transforms the event data into an {@code IssueInvoiceCommand} and passes it to the
     * {@code InvoiceCommandService}.
     * </p>
     *
     * @param event the payment processed event containing payment details
     */
    @EventListener
    public void on(PaymentProcessedEvent event) {
        commandService.handle(new IssueInvoiceCommand(
                event.orderId(), event.amount(), event.currency()
        ));
    }
}