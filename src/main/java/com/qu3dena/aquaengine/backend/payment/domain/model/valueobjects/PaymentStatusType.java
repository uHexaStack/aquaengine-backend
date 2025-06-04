package com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects;

/**
 * Enum representing the different statuses a payment can have.
 */
public enum PaymentStatusType {
    /**
     * Payment is pending and has not been completed.
     */
    PENDING,

    /**
     * Payment has been completed successfully.
     */
    COMPLETED,

    /**
     * Payment has failed.
     */
    FAILED,

    /**
     * Payment has been refunded.
     */
    REFUNDED
}