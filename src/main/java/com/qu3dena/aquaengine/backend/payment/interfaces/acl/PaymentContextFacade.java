package com.qu3dena.aquaengine.backend.payment.interfaces.acl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Facade interface for payment context operations.
 * Provides methods to process, refund, retrieve payment status,
 * and list payments associated with an order.
 */
public interface PaymentContextFacade {

    /**
     * Processes a payment.
     *
     * @param userId   the unique identifier of the user; can be null if not applicable
     * @param orderId  the unique identifier of the order
     * @param amount   the payment amount
     * @param currency the currency of the payment
     * @param method   the payment method
     * @return the unique identifier of the processed payment
     */
    Long processPayment(Long userId, Long orderId, BigDecimal amount, String currency, String method);

    /**
     * Refunds a payment.
     *
     * @param paymentId the unique identifier of the payment to refund
     * @return {@code true} if the refund is successful; otherwise, {@code false}
     */
    boolean refundPayment(Long paymentId);

    /**
     * Retrieves the status of a payment.
     *
     * @param paymentId the unique identifier of the payment
     * @return a {@link String} representing the payment status
     */
    String getPaymentStatus(Long paymentId);

    /**
     * Retrieves a list of payment identifiers associated with a specific order.
     *
     * @param orderId the unique identifier of the order
     * @return a {@link List} of payment identifiers
     */
    List<Long> getPaymentsByOrderId(Long orderId);
}