package com.qu3dena.aquaengine.backend.inventory.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItemAggregate, Long> {

    Optional<InventoryItemAggregate> findByName(String name);

    List<InventoryItemAggregate> findByQuantityOnHandLessThanEqual(int threshold);
}