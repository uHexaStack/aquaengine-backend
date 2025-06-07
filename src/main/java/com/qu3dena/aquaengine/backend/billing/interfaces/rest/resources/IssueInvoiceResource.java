package com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Represents an issue invoice resource for the REST API.
 *
 * <p>
 * This record contains the information required to issue a new invoice,
 * including the order identifier, the amount, and the currency.
 * </p>
 *
 * @param orderId  the unique identifier of the order for which the invoice is to be issued
 * @param amount   the amount of the invoice
 * @param currency the currency of the invoice
 */
public record IssueInvoiceResource(
        Long orderId,
        BigDecimal amount,
        String currency
) {
}