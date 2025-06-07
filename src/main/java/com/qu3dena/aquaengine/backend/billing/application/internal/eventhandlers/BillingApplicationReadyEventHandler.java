package com.qu3dena.aquaengine.backend.billing.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.SeedInvoiceStatusesCommand;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceStatusCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler for {@code ApplicationReadyEvent} used to seed invoice statuses on application startup.
 * <p>
 * Once the application is fully started, this handler calls the {@code InvoiceStatusCommandService}
 * to seed invoice statuses in the system.
 * </p>
 */
@Service
public class BillingApplicationReadyEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingApplicationReadyEventHandler.class);
    private final InvoiceStatusCommandService commandService;

    /**
     * Constructs a new {@code BillingApplicationReadyEventHandler} with the given invoice status command service.
     *
     * @param commandService the service responsible for processing invoice status commands.
     */
    public BillingApplicationReadyEventHandler(InvoiceStatusCommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Handles the {@code ApplicationReadyEvent} by seeding invoice statuses.
     * <p>
     * This method is invoked automatically when the application is ready.
     * It logs the seeding process, calls the invoice status command service to perform the seeding,
     * and logs the completion.
     * </p>
     *
     * @param event the event signaling that the application is fully initialized.
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        LOGGER.info("Seeding invoice statuses...");
        commandService.handle(new SeedInvoiceStatusesCommand());
        LOGGER.info("Invoice statuses seeded.");
    }
}