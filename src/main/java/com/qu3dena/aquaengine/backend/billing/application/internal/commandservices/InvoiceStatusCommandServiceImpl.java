package com.qu3dena.aquaengine.backend.billing.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.SeedInvoiceStatusesCommand;
import com.qu3dena.aquaengine.backend.billing.domain.model.entities.InvoiceStatus;
import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceStatusType;
import com.qu3dena.aquaengine.backend.billing.domain.services.InvoiceStatusCommandService;
import com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories.InvoiceStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Service implementation for handling invoice status commands.
 * <p>
 * This service seeds the database with all possible {@link InvoiceStatusType} values
 * if they do not already exist, using the {@link InvoiceStatusRepository}.
 * </p>
 *
 * @author Gonzalo
 */
@Service
public class InvoiceStatusCommandServiceImpl implements InvoiceStatusCommandService {

    /**
     * Repository for managing {@link InvoiceStatus} entities.
     */
    private final InvoiceStatusRepository invoiceStatusRepository;

    /**
     * Constructs the service with the required repository.
     *
     * @param invoiceStatusRepository the repository for invoice statuses
     */
    public InvoiceStatusCommandServiceImpl(InvoiceStatusRepository invoiceStatusRepository) {
        this.invoiceStatusRepository = invoiceStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedInvoiceStatusesCommand command) {
        Arrays.stream(InvoiceStatusType.values()).forEach(type -> {
            if (!invoiceStatusRepository.existsByName(type)) {
                invoiceStatusRepository.save(InvoiceStatus.create(type));
            }
        });
    }
}
