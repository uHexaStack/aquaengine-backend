package com.qu3dena.aquaengine.backend.billing.domain.model.commands;

import java.math.BigDecimal;

/**
 * Represents a command to issue an invoice.
 * <p>
 * This record encapsulates the order identifier, the invoice amount, and the currency.
 * It validates that the order identifier is provided and positive, that the amount is non-negative,
 * and that the currency is not blank.
 * </p>
 *
 * @param orderId  the unique identifier of the order.
 * @param amount   the monetary amount for the invoice.
 * @param currency the currency in which the invoice amount is specified.
 */
public record IssueInvoiceCommand(
        Long orderId,
        BigDecimal amount,
        String currency
) {
    public IssueInvoiceCommand {
        if (orderId == null || orderId <= 0)
            throw new IllegalArgumentException("OrderId must be provided");

        if (amount == null || amount.signum() < 0)
            throw new IllegalArgumentException("Amount must be non-negative");

        if (currency == null || currency.isBlank())
            throw new IllegalArgumentException("Currency is required");
    }
}