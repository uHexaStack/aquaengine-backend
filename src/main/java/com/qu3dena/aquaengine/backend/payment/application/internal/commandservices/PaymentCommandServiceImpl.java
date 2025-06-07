package com.qu3dena.aquaengine.backend.payment.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.RefundPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentFailedEvent;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentProcessedEvent;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentRefundedEvent;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentCommandService;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentRepository;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentStatusRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for processing and refunding payments.
 * <p>
 * This class handles commands to process payments and refund payments by interacting with the corresponding
 * repositories and publishing appropriate events.
 * </p>
 */
@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructs a new PaymentCommandServiceImpl.
     *
     * @param paymentRepository       the repository for payment aggregates
     * @param paymentStatusRepository the repository for payment statuses
     * @param eventPublisher          the event publisher used to publish payment events
     */
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository, PaymentStatusRepository paymentStatusRepository, ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the process payment command.
     * <p>
     * The process follows these steps:
     * <ol>
     *   <li>Retrieve the PENDING payment status from the status repository.</li>
     *   <li>Create a payment aggregate with the PENDING status.</li>
     *   <li>Determine the next payment status by calling process() on the aggregate.</li>
     *   <li>Obtain the corresponding payment status entity.</li>
     *   <li>Update the payment aggregate status and save the changes.</li>
     *   <li>Publish a PaymentProcessedEvent if the payment is completed or a PaymentFailedEvent otherwise.</li>
     * </ol>
     * </p>
     *
     * @param command the command containing the payment processing data
     * @return an Optional containing the saved PaymentAggregate
     */
    @Override
    public Optional<PaymentAggregate> handle(ProcessPaymentCommand command) {

        // 1) Get the PaymentStatus entity for PENDING
        var pendingOpt = paymentStatusRepository.findByName(PaymentStatusType.PENDING);
        if (pendingOpt.isEmpty())
            throw new RuntimeException("Payment status PENDING not found");

        // 2) Create the PaymentAggregate with the PENDING status
        var payment = PaymentAggregate.create(command, pendingOpt.get());
        var saved = paymentRepository.save(payment);

        // 3) Determinate the next status by calling process() on the aggregate
        var nextStatusOpt = saved.process();
        if (nextStatusOpt.isEmpty())
            throw new RuntimeException("Unexpected: process() returned empty for payment ID " + saved.getId());
        var nextStatus = nextStatusOpt.get();

        // 4) Obtain the PaymentStatus entity for the next status
        var statusEntity = paymentStatusRepository
                .findByName(nextStatus)
                .orElseThrow(() -> new RuntimeException("Payment status " + nextStatus + " not found"));

        // 5) Set the payment status to the next status and save the payment
        saved.setStatus(statusEntity);
        paymentRepository.save(saved);

        // 6) Publish the appropriate event based on the next status
        if (nextStatus == PaymentStatusType.COMPLETED) {
            eventPublisher.publishEvent(new PaymentProcessedEvent(saved.getUserId(), saved.getId(), saved.getOrderId()));
        } else {
            eventPublisher.publishEvent(
                    new PaymentFailedEvent(saved.getUserId(), saved.getOrderId(), "Invalid amount or processing error"));
        }

        return Optional.of(saved);
    }

    /**
     * Handles the refund payment command.
     * <p>
     * The refund process follows these steps:
     * <ol>
     *   <li>Retrieve the existing payment aggregate by its ID.</li>
     *   <li>Verify that the payment is eligible for a refund.</li>
     *   <li>Get the REFUNDED payment status entity from the repository.</li>
     *   <li>Update the payment aggregate with the REFUNDED status and persist the change.</li>
     *   <li>Build and publish a PaymentRefundedEvent.</li>
     * </ol>
     * </p>
     *
     * @param command the command containing the refund payment request data
     * @return an Optional containing a pair with the updated PaymentAggregate and the PaymentRefundedEvent
     */
    @Override
    public Optional<ImmutablePair<PaymentAggregate, PaymentRefundedEvent>> handle(RefundPaymentCommand command) {

        // 1) Retrieve the payment by ID
        var maybePayment = paymentRepository.findById(command.paymentId());
        if (maybePayment.isEmpty())
            return Optional.empty();

        PaymentAggregate payment = maybePayment.get();

        // 2) Verify that the payment is eligible for refund
        PaymentStatusType nextStatusType = payment.refund();

        // 3) Get the PaymentStatus entity for the REFUNDED status
        var statusEntityOpt = paymentStatusRepository.findByName(nextStatusType);
        if (statusEntityOpt.isEmpty())
            throw new RuntimeException("Payment status REFUNDED not found");
        var persistedStatus = statusEntityOpt.get();

        // 4) Set the payment status to REFUNDED and save the payment
        payment.setStatus(persistedStatus);
        var saved = paymentRepository.save(payment);

        // 5) Build the PaymentRefundedEvent and publish it
        var refundEvent = new PaymentRefundedEvent(saved.getId(), saved.getOrderId());
        eventPublisher.publishEvent(refundEvent);

        return Optional.of(ImmutablePair.of(saved, refundEvent));
    }
}