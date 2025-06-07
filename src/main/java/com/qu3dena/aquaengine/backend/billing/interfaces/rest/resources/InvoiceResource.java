package com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents an invoice resource for the REST API.
 *
 * @param id            the unique identifier of the invoice
 * @param invoiceNumber the invoice number
 * @param orderId       the identifier of the associated order
 * @param amount        the invoice amount
 * @param currency      the currency of the invoice
 * @param status        the current invoice status
 * @param issuedAt      the timestamp when the invoice was issued
 */
public record InvoiceResource(
        Long id,
        String invoiceNumber,
        Long orderId,
        BigDecimal amount,
        String currency,
        String status,
        Instant issuedAt
) {
}