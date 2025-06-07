package com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects;

/**
 * Represents the status types for an invoice.
 * <p>
 * This enumeration defines the various states that an invoice can have throughout its lifecycle.
 * </p>
 */
public enum InvoiceStatusType {
    /**
     * Indicates that the invoice is in a draft state.
     */
    DRAFT,

    /**
     * Indicates that the invoice has been issued.
     */
    ISSUE,

    /**
     * Indicates that the invoice has been cancelled.
     */
    CANCELLED
}