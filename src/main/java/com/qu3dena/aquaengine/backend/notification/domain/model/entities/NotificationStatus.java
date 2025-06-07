package com.qu3dena.aquaengine.backend.notification.domain.model.entities;

import com.qu3dena.aquaengine.backend.notification.domain.model.valueobjects.NotificationStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the status of a notification.
 * <p>
 * This entity is used to persist notification statuses in the system. The status is defined by a
 * specific type represented by {@link NotificationStatusType}.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_statuses")
public class NotificationStatus {

    /**
     * The unique identifier of the NotificationStatus.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique and non-null name of the notification status, represented by {@link NotificationStatusType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private NotificationStatusType name;

    /**
     * Constructs a NotificationStatus with the specified status type.
     *
     * @param name the type of the notification status
     */
    public NotificationStatus(NotificationStatusType name) {
        this.name = name;
    }
}