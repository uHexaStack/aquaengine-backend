package com.qu3dena.aquaengine.backend.billing.domain.services;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;

import java.util.Optional;

/**
 * Service interface for processing invoice commands.
 * <p>
 * This interface declares an operation for handling invoice issuance commands
 * and returning an associated invoice aggregate.
 * </p>
 */
public interface InvoiceCommandService {

    /**
     * Processes the given {@code IssueInvoiceCommand} and returns an optional
     * invoice aggregate.
     *
     * @param command the invoice command containing necessary details to issue an invoice.
     * @return an {@code Optional} containing the resulting {@code InvoiceAggregate} if successful,
     * or an empty optional if not.
     */
    Optional<InvoiceAggregate> handle(IssueInvoiceCommand command);
}