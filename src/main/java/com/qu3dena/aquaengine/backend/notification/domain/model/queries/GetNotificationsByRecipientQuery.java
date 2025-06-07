package com.qu3dena.aquaengine.backend.notification.domain.model.queries;

/**
 * Represents a query to retrieve notifications by recipient.
 * <p>
 * This query validates that the recipient is not null or blank.
 * </p>
 *
 * @param recipient the recipient whose notifications are to be retrieved
 */
public record GetNotificationsByRecipientQuery(String recipient) {
    public GetNotificationsByRecipientQuery {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("Recipient cannot be null or blank");
        }
    }
}