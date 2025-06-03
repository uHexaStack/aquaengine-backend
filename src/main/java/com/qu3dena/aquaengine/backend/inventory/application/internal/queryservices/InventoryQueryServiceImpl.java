package com.qu3dena.aquaengine.backend.inventory.application.internal.queryservices;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetInventoryByProductIdQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.model.queries.GetLowStockItemsQuery;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryQueryService;
import com.qu3dena.aquaengine.backend.inventory.infrastructure.persistence.jpa.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link InventoryQueryService} to handle inventory queries.
 * <p>
 * Each method inherits its documentation from the interface.
 * </p>
 */
@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Constructs a new instance of {@code InventoryQueryServiceImpl} with the specified inventory repository.
     *
     * @param inventoryRepository the repository used to access inventory data.
     */
    public InventoryQueryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InventoryItemAggregate> handle(GetInventoryByProductIdQuery query) {
        return inventoryRepository.findByProductId(query.productId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InventoryItemAggregate> handle(GetLowStockItemsQuery query) {
        return inventoryRepository.findByAvailableQuantity_AmountLessThanEqual(query.threshold());
    }
}