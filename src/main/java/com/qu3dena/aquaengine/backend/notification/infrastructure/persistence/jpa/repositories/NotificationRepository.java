package com.qu3dena.aquaengine.backend.notification.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.notification.domain.model.aggregates.NotificationAggregate;
import com.qu3dena.aquaengine.backend.notification.domain.model.valueobjects.NotificationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationAggregate, Integer> {

    List<NotificationAggregate> findByStatus_Name(NotificationStatusType statusName);

    List<NotificationAggregate> findByRecipient(String recipient);
}
