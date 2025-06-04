package com.qu3dena.aquaengine.backend.payment.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.SeedPaymentStatusesCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.entities.PaymentStatus;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentStatusCommandService;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of the {@code PaymentStatusCommandService}.
 * Provides a method to seed payment statuses into the repository.
 */
@Service
public class PaymentStatusCommandServiceImpl implements PaymentStatusCommandService {

    private final PaymentStatusRepository paymentStatusRepository;

    /**
     * Constructs a new {@code PaymentStatusCommandServiceImpl}.
     *
     * @param paymentStatusRepository the repository used to manage payment statuses
     */
    public PaymentStatusCommandServiceImpl(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * Handles the {@code SeedPaymentStatusesCommand} by ensuring that all
     * payment status types are saved in the repository.
     *
     * @param command the command to seed payment statuses
     */
    @Override
    public void handle(SeedPaymentStatusesCommand command) {
        Arrays.stream(PaymentStatusType.values()).forEach(type -> {
            if (!paymentStatusRepository.existsByName(type)) {
                paymentStatusRepository.save(PaymentStatus.create(type.name()));
            }
        });
    }
}