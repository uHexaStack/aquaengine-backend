package com.qu3dena.aquaengine.backend.payment.domain.services;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.SeedPaymentStatusesCommand;

/**
 * Service interface for handling payment status commands.
 */
public interface PaymentStatusCommandService {

    /**
     * Processes the given {@code SeedPaymentStatusesCommand} to seed payment statuses.
     *
     * @param command the command containing instructions to seed payment statuses
     */
    void handle(SeedPaymentStatusesCommand command);
}