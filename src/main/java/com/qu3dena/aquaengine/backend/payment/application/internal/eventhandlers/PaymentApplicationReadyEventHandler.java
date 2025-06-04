package com.qu3dena.aquaengine.backend.payment.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.SeedPaymentStatusesCommand;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentStatusCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Handles the application ready event for the payment module.
 * This class seeds payment statuses when the Spring Boot application has started.
 */
@Service
public class PaymentApplicationReadyEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentApplicationReadyEventHandler.class);
    private final PaymentStatusCommandService paymentStatusCommandService;

    /**
     * Constructs a new {@code PaymentApplicationReadyEventHandler}.
     *
     * @param paymentStatusCommandService the service responsible for handling payment status commands
     */
    public PaymentApplicationReadyEventHandler(PaymentStatusCommandService paymentStatusCommandService) {
        this.paymentStatusCommandService = paymentStatusCommandService;
    }

    /**
     * Handles the {@code ApplicationReadyEvent} by seeding payment statuses.
     *
     * @param event the application ready event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        LOGGER.info("Application is ready. Seeding payment statuses...");
        paymentStatusCommandService.handle(new SeedPaymentStatusesCommand());
        LOGGER.info("Payment statuses seeded successfully.");
    }
}