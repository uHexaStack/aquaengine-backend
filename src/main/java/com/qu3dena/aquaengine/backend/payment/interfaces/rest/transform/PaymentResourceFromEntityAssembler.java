package com.qu3dena.aquaengine.backend.payment.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources.PaymentResource;

/**
 * Assembler class for converting a {@code PaymentAggregate} entity into a
 * {@code PaymentResource} record.
 */
public class PaymentResourceFromEntityAssembler {

    /**
     * Converts a {@code PaymentAggregate} entity into a {@code PaymentResource} record.
     *
     * @param entity the payment aggregate entity containing the payment data
     * @return a {@code PaymentResource} instantiated with the data from the given entity
     */
    public static PaymentResource toResourceFromEntity(PaymentAggregate entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount().amount(),
                entity.getAmount().currency(),
                entity.getStatus().getStringStatus(),
                entity.getPaymentMethod().method(),
                entity.getTransactionDate()
        );
    }
}