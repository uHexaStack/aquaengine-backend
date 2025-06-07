package com.qu3dena.aquaengine.backend.notification.domain.model.entities;

import com.qu3dena.aquaengine.backend.notification.domain.model.valueobjects.NotificationTypeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a notification type.
 * <p>
 * This entity is used to persist notification types in the system.
 * The type is defined by a {@link NotificationTypeType} enumeration.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_types")
public class NotificationType {

    /**
     * The unique identifier of the NotificationType.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique and non-null name of the notification type,
     * represented by {@link NotificationTypeType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private NotificationTypeType name;

    /**
     * Constructs a NotificationType with the specified type.
     *
     * @param name the type of the notification
     */
    public NotificationType(NotificationTypeType name) {
        this.name = name;
    }
}