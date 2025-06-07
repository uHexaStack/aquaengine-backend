package com.qu3dena.aquaengine.backend.notification.domain.services;

import com.qu3dena.aquaengine.backend.notification.domain.model.aggregates.NotificationAggregate;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByRecipientQuery;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByStatusQuery;

import java.util.List;

/**
 * Defines a query service for retrieving notifications.
 * <p>
 * Provides methods to handle queries based on notification status and recipient.
 * </p>
 */
public interface NotificationQueryService {

    /**
     * Retrieves notifications based on the provided status query.
     *
     * @param query the query containing the status criteria
     * @return a list of notifications that match the provided status
     */
    List<NotificationAggregate> handle(GetNotificationsByStatusQuery query);

    /**
     * Retrieves notifications based on the provided recipient query.
     *
     * @param query the query containing the recipient criteria
     * @return a list of notifications for the specified recipient
     */
    List<NotificationAggregate> handle(GetNotificationsByRecipientQuery query);
}