package com.qu3dena.aquaengine.backend.inventory.domain.model.queries;

/**
 * Represents a query to retrieve inventory items with a stock level
 * at or below a defined threshold.
 * <p>
 * The <code>threshold</code> value must be greater than or equal to 0.
 * </p>
 *
 * @param threshold the stock level threshold; items with quantity less than or equal
 *                  to this value are considered low stock.
 */
public record GetLowStockItemsQuery(int threshold) {
    public GetLowStockItemsQuery {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be >= 0");
        }
    }
}