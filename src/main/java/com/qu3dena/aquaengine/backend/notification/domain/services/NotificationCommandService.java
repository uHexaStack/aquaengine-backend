package com.qu3dena.aquaengine.backend.notification.domain.services;

import com.qu3dena.aquaengine.backend.notification.domain.model.aggregates.NotificationAggregate;
import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;

import java.util.Optional;

/**
 * Defines a command service that handles operations related to notifications.
 */
public interface NotificationCommandService {

    /**
     * Processes a command to send a notification.
     *
     * @param command the command containing the notification details to be sent
     * @return an {@code Optional} containing the corresponding {@code NotificationAggregate}
     *         if the command was processed successfully; otherwise, an empty {@code Optional}
     */
    Optional<NotificationAggregate> handle(SendNotificationCommand command);
}