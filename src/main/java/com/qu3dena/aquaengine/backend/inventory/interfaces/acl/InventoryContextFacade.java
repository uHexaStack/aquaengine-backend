package com.qu3dena.aquaengine.backend.inventory.interfaces.acl;

import java.util.List;

/**
 * Facade interface for interacting with the inventory context.
 * <p>
 * Provides methods to create inventory items, adjust inventory levels,
 * reserve and release stock, retrieve available quantity,
 * and get a list of product IDs with low stock.
 * </p>
 */
public interface InventoryContextFacade {

    /**
     * Creates a new inventory item for the specified product.
     *
     * @param productId       the unique identifier of the product.
     * @param initialQuantity the initial quantity to set for the inventory item.
     * @return the unique identifier of the created inventory item.
     */
    Long createInventoryItem(Long productId, int initialQuantity);

    /**
     * Adjusts the inventory level of the specified item.
     *
     * @param itemId   the unique identifier of the inventory item.
     * @param adjustBy the amount by which to adjust the inventory (can be negative).
     * @return true if the adjustment was successful, false otherwise.
     */
    boolean adjustInventory(Long itemId, int adjustBy);

    /**
     * Reserves stock for the specified product.
     *
     * @param productId the unique identifier of the product.
     * @param quantity  the quantity to reserve.
     * @return true if the reservation was successful, false otherwise.
     */
    boolean reserveStock(Long productId, int quantity);

    /**
     * Releases reserved stock for the specified product.
     *
     * @param productId the unique identifier of the product.
     * @param quantity  the quantity to release.
     * @return true if the release was successful, false otherwise.
     */
    boolean releaseStock(Long productId, int quantity);

    /**
     * Retrieves the available quantity for the specified product.
     *
     * @param productId the unique identifier of the product.
     * @return the available quantity.
     */
    int getAvailableQuantity(Long productId);

    /**
     * Retrieves a list of product IDs that have a stock level less than or equal to the provided threshold.
     *
     * @param threshold the low stock threshold.
     * @return a list of product IDs with low stock.
     */
    List<Long> getProductsWithLowStock(int threshold);
}