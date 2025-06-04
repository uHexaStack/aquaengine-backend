package com.qu3dena.aquaengine.backend.payment.domain.model.commands;

import java.util.Objects;

/**
 * Command to refund a payment.
 * <p>
 * This record holds the identifier of the payment to be refunded.
 * </p>
 */
public record RefundPaymentCommand(Long paymentId) {

    /**
     * Constructs a new RefundPaymentCommand.
     *
     * @param paymentId the identifier of the payment to be refunded; must not be null
     * @throws NullPointerException if {@code paymentId} is null
     */
    public RefundPaymentCommand {
        Objects.requireNonNull(paymentId, "PaymentId cannot be null");
    }
}