package com.qu3dena.aquaengine.backend.billing.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;
import com.qu3dena.aquaengine.backend.billing.interfaces.rest.resources.IssueInvoiceResource;

/**
 * Assembler to convert an {@code IssueInvoiceResource} into an {@code IssueInvoiceCommand}.
 * <p>
 * This class provides a method to transform the data from a REST resource into a command
 * that can be processed by the billing domain.
 * </p>
 */
public class IssueInvoiceResourceAssembler {

    /**
     * Converts the given {@code IssueInvoiceResource} to an {@code IssueInvoiceCommand}.
     *
     * @param resource the resource containing details for issuing an invoice.
     * @return an {@code IssueInvoiceCommand} derived from the resource.
     */
    public static IssueInvoiceCommand toCommandFromResource(IssueInvoiceResource resource) {
        return new IssueInvoiceCommand(resource.orderId(), resource.amount(), resource.currency());
    }
}