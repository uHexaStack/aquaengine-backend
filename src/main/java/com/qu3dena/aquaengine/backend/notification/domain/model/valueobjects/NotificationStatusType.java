package com.qu3dena.aquaengine.backend.notification.domain.model.valueobjects;

/**
 * Represents the status of a notification.
 * <p>
 * The available statuses are:
 * <ul>
 *   <li><code>PENDING</code>: the notification is pending.</li>
 *   <li><code>SENT</code>: the notification was sent successfully.</li>
 *   <li><code>FAILED</code>: the notification failed to send.</li>
 * </ul>
 * </p>
 */
public enum NotificationStatusType {
    PENDING,
    SENT,
    FAILED,
}