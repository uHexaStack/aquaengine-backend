package com.qu3dena.aquaengine.backend.billing.application.internal.queryservices;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetAllInvoicesQuery;
import com.qu3dena.aquaengine.backend.billing.domain.model.queries.GetInvoiceByOrderIdQuery;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceQueryService;
import com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the invoice query service.
 * <p>
 * This service handles queries for retrieving invoice aggregates.
 * It delegates calls to the underlying invoice repository.
 * </p>
 */
@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;

    /**
     * Constructs a new instance of {@code InvoiceQueryServiceImpl} with the specified invoice repository.
     *
     * @param invoiceRepository the repository for invoice aggregates.
     */
    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InvoiceAggregate> handle(GetInvoiceByOrderIdQuery query) {
        return invoiceRepository.findByOrderId(query.orderId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InvoiceAggregate> handle(GetAllInvoicesQuery query) {
        return invoiceRepository.findAll();
    }
}