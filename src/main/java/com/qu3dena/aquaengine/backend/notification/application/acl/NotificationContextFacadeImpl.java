package com.qu3dena.aquaengine.backend.notification.application.acl;

import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByRecipientQuery;
import com.qu3dena.aquaengine.backend.notification.domain.model.queries.GetNotificationsByStatusQuery;
import com.qu3dena.aquaengine.backend.notification.domain.services.NotificationCommandService;
import com.qu3dena.aquaengine.backend.notification.domain.services.NotificationQueryService;
import com.qu3dena.aquaengine.backend.notification.interfaces.acl.NotificationContextFacade;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationContextFacadeImpl implements NotificationContextFacade {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationContextFacadeImpl(NotificationCommandService notificationCommandService,
                                         NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    @Override
    public Long sendNotification(String type,
                                 String recipient,
                                 String payload,
                                 Long orderId,
                                 Long invoiceId,
                                 Long paymentId) {
        return notificationCommandService.handle(new SendNotificationCommand(type, recipient, payload, orderId, invoiceId, paymentId))
                .map(AuditableAbstractAggregateRoot::getId)
                .orElse(0L);
    }

    @Override
    public List<Long> getNotificationsByStatus(String status) {
        return notificationQueryService.handle(new GetNotificationsByStatusQuery(status))
                .stream()
                .map(AuditableAbstractAggregateRoot::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getNotificationsByRecipient(String recipient) {
        return notificationQueryService.handle(new GetNotificationsByRecipientQuery(recipient))
                .stream()
                .map(AuditableAbstractAggregateRoot::getId)
                .collect(Collectors.toList());
    }
}