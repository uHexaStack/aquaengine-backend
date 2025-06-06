package com.qu3dena.aquaengine.backend.inventory.interfaces.acl;

import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;

import java.util.List;

public interface InventoryContextFacade {

    Long createInventoryItem(String name, Money price, int initialQuantity, int threshold);

    boolean adjustInventory(Long itemId, int adjustBy);

    boolean reserveStock(Long itemId, int quantity);

    boolean releaseStock(Long itemId, int quantity);

    int getAvailableQuantity(String name);

    List<String> getItemsWithLowStock(String name);
}