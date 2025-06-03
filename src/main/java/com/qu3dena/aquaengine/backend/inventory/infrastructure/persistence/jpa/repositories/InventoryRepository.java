package com.qu3dena.aquaengine.backend.inventory.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on
 * {@link InventoryItemAggregate} entities.
 * <p>
 * Provides methods to find inventory items by product id and by available quantity threshold.
 * </p>
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryItemAggregate, Long> {

    /**
     * Repository interface for performing CRUD operations on
     * {@link InventoryItemAggregate} entities.
     * <p>
     * Provides methods to find inventory items by product id and by available quantity threshold.
     * </p>
     */
    Optional<InventoryItemAggregate> findByProductId(Long id);

    /**
     * Retrieves a list of inventory item aggregates whose available quantity amount
     * is less than or equal to the specified threshold.
     *
     * @param threshold the maximum available quantity value.
     * @return a list of matching {@link InventoryItemAggregate} objects,
     * or an empty list if no items meet the criteria.
     */
    List<InventoryItemAggregate> findByAvailableQuantity_AmountLessThanEqual(int threshold);
}