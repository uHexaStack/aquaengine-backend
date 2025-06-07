package com.qu3dena.aquaengine.backend.notification.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.notification.domain.model.entities.NotificationType;
import com.qu3dena.aquaengine.backend.notification.domain.model.valueobjects.NotificationTypeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    Optional<NotificationType> findByName(NotificationTypeType name);

    boolean existsByName(NotificationTypeType name);
}
