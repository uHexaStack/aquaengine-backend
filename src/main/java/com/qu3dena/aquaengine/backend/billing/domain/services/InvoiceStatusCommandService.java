package com.qu3dena.aquaengine.backend.billing.domain.services;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.SeedInvoiceStatusesCommand;

/**
 * Service interface for handling invoice status commands.
 * <p>
 * This interface declares operations for processing commands related to invoice statuses,
 * such as seeding the default invoice statuses.
 * </p>
 */
public interface InvoiceStatusCommandService {

    /**
     * Handles the given {@code SeedInvoiceStatusesCommand} to seed the default invoice statuses.
     *
     * @param command the command containing instructions for seeding invoice statuses.
     */
    void handle(SeedInvoiceStatusesCommand command);
}