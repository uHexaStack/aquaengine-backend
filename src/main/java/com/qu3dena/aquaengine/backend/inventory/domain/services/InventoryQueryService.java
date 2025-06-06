package com.qu3dena.aquaengine.backend.inventory.domain.services;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByNameQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemsQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryService {


    Optional<InventoryItemAggregate> handle(GetInventoryByNameQuery query);


    List<InventoryItemAggregate> handle(GetLowStockItemsQuery query);
}