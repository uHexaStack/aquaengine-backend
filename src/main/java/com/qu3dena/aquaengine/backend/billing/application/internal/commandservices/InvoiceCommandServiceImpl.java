package com.qu3dena.aquaengine.backend.billing.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;
import com.qu3dena.aquaengine.backend.billing.domain.model.events.InvoiceFailedEvent;
import com.qu3dena.aquaengine.backend.billing.domain.model.events.InvoiceIssuedEvent;
import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceNumber;
import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceStatusType;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceCommandService;
import com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories.InvoiceStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for processing invoice commands.
 * <p>
 * This service handles the issuance of invoices by validating the invoice status,
 * creating the invoice aggregate, persisting it and publishing related events.
 * </p>
 */
@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {

    private final InvoiceRepository invoiceRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final InvoiceStatusRepository invoiceStatusRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceCommandServiceImpl.class);

    /**
     * Constructs the InvoiceCommandServiceImpl with required dependencies.
     *
     * @param invoiceRepository       the repository for invoice aggregates.
     * @param invoiceStatusRepository the repository for invoice statuses.
     * @param eventPublisher          the application event publisher.
     */
    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository, InvoiceStatusRepository invoiceStatusRepository, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.invoiceRepository = invoiceRepository;
        this.invoiceStatusRepository = invoiceStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InvoiceAggregate> handle(IssueInvoiceCommand command) {
        try {
            var status = invoiceStatusRepository.findByName(InvoiceStatusType.ISSUE)
                    .orElseThrow(() -> new IllegalArgumentException("Invoice status not found"));

            var number = InvoiceNumber.generate();

            var invoice = InvoiceAggregate.create(
                    command,
                    status,
                    number
            );

            var saved = invoiceRepository.save(invoice);

            eventPublisher.publishEvent(new InvoiceIssuedEvent(
                    saved.getId(),
                    saved.getOrderId(),
                    saved.getInvoiceNumber(),
                    saved.getIssuedAt(),
                    saved.getAmount(),
                    saved.getCurrency()
            ));

            return Optional.of(saved);
        } catch (Exception ex) {
            LOGGER.error("Failed to issue invoice for order {}: {}", command.orderId(), ex.getMessage());

            eventPublisher.publishEvent(new InvoiceFailedEvent(
                    command.orderId(),
                    ex.getMessage()
            ));

            return Optional.empty();
        }
    }
}