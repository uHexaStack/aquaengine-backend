package com.qu3dena.aquaengine.backend.notification.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;
import com.qu3dena.aquaengine.backend.notification.domain.model.entities.NotificationStatus;
import com.qu3dena.aquaengine.backend.notification.domain.model.entities.NotificationType;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a notification aggregate.
 * This entity encompasses the details necessary to process and persist a notification.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "notifications")
@EqualsAndHashCode(callSuper = true)
public class NotificationAggregate extends AuditableAbstractAggregateRoot<NotificationAggregate> {

    /**
     * The type of the notification.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private NotificationType type;

    /**
     * The status of the notification.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private NotificationStatus status;

    /**
     * The recipient of the notification.
     */
    @Column(nullable = false)
    private String recipient;

    /**
     * The payload or message content of the notification.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    /**
     * The time when the notification was sent.
     */
    @Column
    private Instant sentAt;

    /**
     * The associated order ID, if any.
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * The associated invoice ID, if any.
     */
    @Column(name = "invoice_id")
    private Long invoiceId;

    /**
     * The associated payment ID, if any.
     */
    @Column(name = "payment_id")
    private Long paymentId;

    /**
     * Constructs a NotificationAggregate with the specified notification command, type and status.
     *
     * @param command       the command containing notification details
     * @param typeEntity    the notification type entity
     * @param pendingStatus the initial notification status
     */
    public NotificationAggregate(
            SendNotificationCommand command,
            NotificationType typeEntity,
            NotificationStatus pendingStatus
    ) {
        this.type = typeEntity;
        this.status = pendingStatus;
        this.recipient = command.recipient();
        this.payload = command.payload();
        this.orderId = command.orderId();
        this.invoiceId = command.invoiceId();
        this.paymentId = command.paymentId();
    }

    /**
     * Creates and returns a new NotificationAggregate instance.
     *
     * @param command       the command containing notification details
     * @param typeEntity    the notification type entity
     * @param pendingStatus the initial notification status
     * @return a new instance of NotificationAggregate
     */
    public static NotificationAggregate create(SendNotificationCommand command, NotificationType typeEntity, NotificationStatus pendingStatus) {
        return new NotificationAggregate(command, typeEntity, pendingStatus);
    }
}