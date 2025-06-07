package com.qu3dena.aquaengine.backend.billing.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources.InvoiceResource;

/**
 * Assembler class to transform an invoice aggregate entity into an invoice resource.
 * <p>
 * This class provides a method to convert an {@code InvoiceAggregate} instance into a
 * corresponding {@code InvoiceResource} suitable for REST responses.
 * </p>
 */
public class InvoiceResourceFromEntityAssembler {

    /**
     * Transforms the given invoice aggregate entity into an invoice resource.
     *
     * @param entity the invoice aggregate entity to transform.
     * @return an {@code InvoiceResource} representing the invoice aggregate.
     */
    public static InvoiceResource toResourceFromEntity(InvoiceAggregate entity) {
        return new InvoiceResource(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getOrderId(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getStatus().getName().name(),
                entity.getIssuedAt()
        );
    }
}