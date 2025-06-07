package com.qu3dena.aquaengine.backend.notification.domain.model.events;

/**
 * Represents an event indicating that a notification has failed.
 * <p>
 * This event is used in the system to signal a failure when sending or processing a notification,
 * capturing the notification identifier and a description of the failure reason.
 * </p>
 *
 * @param notificationId the unique identifier of the notification that failed
 * @param reason the reason for the notification failure
 */
public record NotificationFailedEvent(Long notificationId, String reason) {
}