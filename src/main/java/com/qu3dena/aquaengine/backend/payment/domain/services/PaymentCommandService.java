package com.qu3dena.aquaengine.backend.payment.domain.services;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.RefundPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentRefundedEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

/**
 * Service interface to handle commands related to payment operations.
 */
public interface PaymentCommandService {

    /**
     * Handles the processing of a payment command.
     *
     * @param command the command containing details for processing the payment
     * @return an Optional containing the created PaymentAggregate if processing is successful; otherwise, an empty Optional
     */
    Optional<PaymentAggregate> handle(ProcessPaymentCommand command);

    /**
     * Handles the refunding of a payment based on the refund command.
     *
     * @param command the command containing details for refunding the payment
     * @return an Optional containing a pair of PaymentAggregate and PaymentRefundedEvent if refund is successful; otherwise, an empty Optional
     */
    Optional<ImmutablePair<PaymentAggregate, PaymentRefundedEvent>> handle(RefundPaymentCommand command);
}