package com.qu3dena.aquaengine.backend.inventory.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItemAggregate, Long> {

    List<InventoryItemAggregate> findByUserId(Long userId);

    Optional<InventoryItemAggregate> findByUserIdAndName(Long userId, String name);

    List<InventoryItemAggregate> findByUserIdAndQuantityOnHandLessThanEqual(Long userId, int threshold);
}