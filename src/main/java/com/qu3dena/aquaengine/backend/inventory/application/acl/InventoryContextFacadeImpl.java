package com.qu3dena.aquaengine.backend.inventory.application.acl;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.AdjustInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByNameQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemByNameQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryQueryService;
import com.qu3dena.aquaengine.backend.inventory.interfaces.acl.InventoryContextFacade;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    public InventoryContextFacadeImpl(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @Override
    public Long createInventoryItem(String name, Money price, int initialQuantity, int threshold) {
        var command = new CreateInventoryItemCommand(name, price, initialQuantity, threshold);

        return inventoryCommandService.handle(command)
                .map(AuditableAbstractAggregateRoot::getId)
                .orElse(0L);
    }

    @Override
    public boolean adjustInventory(Long itemId, int adjustBy) {
        try {
            var command = new AdjustInventoryCommand(itemId, adjustBy);
            inventoryCommandService.handle(command);
            return true;
        } catch (
                Exception e) {
            return false;
        }
    }

    @Override
    public boolean reserveStock(Long itemId, int quantity) {
        try {
            var command = new ReserveInventoryCommand(itemId, quantity);
            inventoryCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean releaseStock(Long itemId, int quantity) {
        try {
            var command = new ReserveInventoryCommand(itemId, -quantity);
            inventoryCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getAvailableQuantity(String name) {
        var query = new GetInventoryByNameQuery(name);

        var maybe = inventoryQueryService.handle(query);

        return maybe.map(InventoryItemAggregate::getQuantityOnHand).orElse(-1);
    }

    @Override
    public List<String> getItemsWithLowStock(String name) {
        var query = new GetLowStockItemByNameQuery(name);
        var items = inventoryQueryService.handle(query);

        return items.stream()
                .map(InventoryItemAggregate::getName)
                .toList();
    }
}
