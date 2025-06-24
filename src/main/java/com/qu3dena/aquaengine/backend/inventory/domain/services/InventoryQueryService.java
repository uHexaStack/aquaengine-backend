package com.qu3dena.aquaengine.backend.inventory.domain.services;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryService {


    Optional<InventoryItemAggregate> handle(GetInventoryByUserIdAndNameQuery query);


    Optional<InventoryItemAggregate> handle(GetLowStockItemByNameQuery query);

    List<InventoryItemAggregate> handle(GetLowStockItemsQuery query);

    Optional<InventoryItemAggregate> handle(GetInventoryItemByIdQuery query);

    List<InventoryItemAggregate> handle(GetInventoryItemsByUserIdQuery query);

    List<InventoryItemAggregate> handle(GetAllInventoryItemsQuery query);
}