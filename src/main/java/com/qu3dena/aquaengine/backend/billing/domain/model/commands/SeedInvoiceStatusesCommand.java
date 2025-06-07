package com.qu3dena.aquaengine.backend.billing.domain.model.commands;

/**
 * Command used to seed the default invoice statuses into the application.
 * <p>
 * This command is typically used during the application startup process to ensure that
 * the necessary invoice status values are present in the system.
 * </p>
 */
public record SeedInvoiceStatusesCommand() {
}