package com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Optional<OrderStatus> findByName(OrderStatusType name);

    Boolean existsByName(OrderStatusType name);
}
