package com.qu3dena.aquaengine.backend.inventory.domain.services;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.AdjustInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.events.StockLowEvent;

import java.util.Optional;

/**
 * Provides methods for executing inventory commands.
 * <p>
 * Each method handles a specific command and returns an {@link Optional} with the resulting
 * aggregate or event. If the command fails or does not result in a change, an empty {@code Optional}
 * or no value (in the case of a void method) is returned.
 * </p>
 */
public interface InventoryCommandService {

    /**
     * Handles the creation of a new inventory item.
     * <p>
     * Returns an {@link Optional} containing the generated {@link InventoryItemAggregate} if created successfully.
     * Otherwise, returns an empty {@code Optional}.
     * </p>
     *
     * @param command the {@link CreateInventoryItemCommand} containing the required data to create an inventory item.
     * @return an {@link Optional} of {@link InventoryItemAggregate} or empty if creation fails.
     */
    Optional<InventoryItemAggregate> handle(CreateInventoryItemCommand command);

    /**
     * Handles adjusting the stock level of an existing inventory item.
     * <p>
     * Returns an {@link Optional} containing a {@link StockLowEvent} if the adjusted stock level
     * falls at or below the low stock threshold. Otherwise, returns an empty {@code Optional}.
     * </p>
     *
     * @param command the {@link AdjustInventoryCommand} representing the adjustment request.
     * @return an {@link Optional} of {@link StockLowEvent} if the stock is low; otherwise, empty.
     */
    Optional<StockLowEvent> handle(AdjustInventoryCommand command);

    /**
     * Handles reserving stock for an inventory item.
     * <p>
     * Returns an {@link Optional} containing a {@link StockLowEvent} if, after reserving stock, the available
     * quantity becomes low. If there is sufficient stock remaining, returns an empty {@code Optional}.
     * </p>
     *
     * @param command the {@link ReserveInventoryCommand} specifying the reservation details.
     * @return an {@link Optional} of {@link StockLowEvent} if the stock becomes low; otherwise, empty.
     */
    Optional<StockLowEvent> handle(ReserveInventoryCommand command);

    /**
     * Handles releasing reserved stock for an inventory item.
     * <p>
     * This method does not return any value. It performs the release operation and updates the available quantity.
     * </p>
     *
     * @param command the {@link ReleaseInventoryCommand} specifying the quantity to be released.
     */
    void handle(ReleaseInventoryCommand command);
}