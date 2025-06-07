package com.qu3dena.aquaengine.backend.inventory.domain.services;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByUserIdAndNameQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryItemByIdQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemByNameQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemsQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryService {


    Optional<InventoryItemAggregate> handle(GetInventoryByUserIdAndNameQuery query);


    Optional<InventoryItemAggregate> handle(GetLowStockItemByNameQuery query);

    List<InventoryItemAggregate> handle(GetLowStockItemsQuery query);

    Optional<InventoryItemAggregate> handle(GetInventoryItemByIdQuery query);
}