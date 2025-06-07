package com.qu3dena.aquaengine.backend.billing.domain.model.events;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents an event indicating that an invoice has been issued.
 * <p>
 * This event contains key details about the invoice including its id, associated order id,
 * invoice number, the issuance time, the monetary amount, and the currency symbol.
 * </p>
 *
 * @param invoiceId     the identifier of the issued invoice
 * @param orderId       the identifier of the associated order
 * @param invoiceNumber the invoice number as a string
 * @param issuedAt      the timestamp when the invoice was issued
 * @param amount        the monetary amount of the invoice
 * @param currency      the currency symbol associated with the amount
 */
public record InvoiceIssuedEvent(
        Long invoiceId,
        Long orderId,
        String invoiceNumber,
        Instant issuedAt,
        BigDecimal amount,
        String currency
) {
}