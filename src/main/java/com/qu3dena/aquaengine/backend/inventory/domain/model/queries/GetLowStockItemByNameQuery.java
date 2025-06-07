package com.qu3dena.aquaengine.backend.inventory.domain.model.queries;

/**
 * Query to retrieve low stock items by their name.
 * <p>
 * This query is used to fetch inventory items that are considered low in stock based on the provided name.
 * </p>
 *
 * @param name the name of the inventory item to search for.
 */
public record GetLowStockItemByNameQuery(Long userId, String name) {
    public GetLowStockItemByNameQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }
}