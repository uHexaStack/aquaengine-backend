package com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderAggregate, Long> {

    List<OrderAggregate> findByUserId(Long userId);
}
