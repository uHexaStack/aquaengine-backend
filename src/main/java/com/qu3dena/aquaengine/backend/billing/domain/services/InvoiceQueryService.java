package com.qu3dena.aquaengine.backend.billing.domain.services;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetAllInvoicesQuery;
import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetInvoiceByOrderIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for querying invoices.
 * <p>
 * This interface declares operations for retrieving invoices based on different query types.
 * </p>
 */
public interface InvoiceQueryService {

    /**
     * Processes the given {@code GetInvoiceByOrderIdQuery} and returns an optional
     * invoice aggregate.
     *
     * @param query the query containing the order identifier for invoice retrieval.
     * @return an {@code Optional} containing the resulting {@code InvoiceAggregate} if found,
     * or an empty optional if not.
     */
    Optional<InvoiceAggregate> handle(GetInvoiceByOrderIdQuery query);

    /**
     * Processes the given {@code GetAllInvoicesQuery} and returns a list of invoice aggregates.
     *
     * @param query the query for retrieving all invoices.
     * @return a {@code List} of all {@code InvoiceAggregate} objects.
     */
    List<InvoiceAggregate> handle(GetAllInvoicesQuery query);
}