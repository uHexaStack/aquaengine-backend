package com.qu3dena.aquaengine.backend.inventory.domain.services;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByProductIdQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemsQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for querying inventory items.
 * <p>
 * Provides methods to retrieve inventory items based on specific queries.
 * </p>
 */
public interface InventoryQueryService {

    /**
     * Handles the {@link GetInventoryByProductIdQuery} to retrieve a specific
     * inventory item aggregate using the provided product ID.
     * <p>
     * Returns an {@link Optional} containing the {@link InventoryItemAggregate} if the item exists.
     * Otherwise, returns an empty {@code Optional}.
     * </p>
     *
     * @param query the query containing the product ID for which the inventory item is requested.
     * @return an {@link Optional} of {@link InventoryItemAggregate} containing the inventory item,
     * or empty if no matching item is found.
     */
    Optional<InventoryItemAggregate> handle(GetInventoryByProductIdQuery query);

    /**
     * Handles the {@link GetLowStockItemsQuery} to retrieve a list of inventory item aggregates
     * that have a low stock level according to the specified threshold.
     * <p>
     * Returns a list of {@link InventoryItemAggregate} objects that are at or below the defined low stock threshold.
     * If no items meet the criteria, an empty list is returned.
     * </p>
     *
     * @param query the query containing the low stock threshold value.
     * @return a list of {@link InventoryItemAggregate} objects with low stock,
     * or an empty list if no inventory items are at or below the threshold.
     */
    List<InventoryItemAggregate> handle(GetLowStockItemsQuery query);
}