package com.qu3dena.aquaengine.backend.payment.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.interfaces.rest.resources.CreatePaymentResource;

/**
 * Assembler class for converting a {@code CreatePaymentResource} into a {@code ProcessPaymentCommand}.
 */
public class CreatePaymentCommandFromResourceAssembler {

    /**
     * Converts a {@code CreatePaymentResource} into a {@code ProcessPaymentCommand}.
     *
     * @param resource the resource containing payment creation data
     * @return a {@code ProcessPaymentCommand} instantiated with the data from the given resource
     */
    public static ProcessPaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new ProcessPaymentCommand(
                resource.userId(),
                resource.orderId(),
                resource.amount(),
                resource.currency(),
                resource.method()
        );
    }
}