package com.qu3dena.aquaengine.backend.billing.domain.model.queries;

/**
 * Query for retrieving an invoice based on the order identifier.
 * <p>
 * This record encapsulates the order identifier used in the process of
 * retrieving a specific invoice.
 * </p>
 *
 * @param orderId the unique identifier of the order.
 */
public record GetInvoiceByOrderIdQuery(Long orderId) {
    public GetInvoiceByOrderIdQuery {
        if (orderId == null || orderId <= 0)
            throw new IllegalArgumentException("OrderId is required");
    }
}