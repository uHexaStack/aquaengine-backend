package com.qu3dena.aquaengine.backend.inventory.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.AdjustInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.events.StockLowEvent;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.inventory.infrastructure.persistence.jpa.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link InventoryCommandService} providing methods to handle various inventory commands.
 * <p>
 * Each method inherits its documentation from the interface.
 * </p>
 */
@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final InventoryRepository inventoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructs a new instance with the specified inventory repository.
     *
     * @param inventoryRepository the repository used to access and manipulate inventory aggregates.
     */
    public InventoryCommandServiceImpl(InventoryRepository inventoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InventoryItemAggregate> handle(CreateInventoryItemCommand command) {
        var exists = inventoryRepository.findByUserIdAndName(command.userId(), command.name());

        if (exists.isPresent())
            throw new RuntimeException("Inventory item for product " + command.name() + " already exists");

        var item = InventoryItemAggregate.create(command);

        inventoryRepository.save(item);

        return Optional.of(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<StockLowEvent> handle(AdjustInventoryCommand command) {
        var item = inventoryRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found: " + command.itemId()));

        var maybeLow = item.adjustStock(command);

        inventoryRepository.save(item);

        maybeLow.ifPresent(eventPublisher::publishEvent);

        return maybeLow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<StockLowEvent> handle(ReserveInventoryCommand command) {
        var item = inventoryRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found: " + command.itemId()));

        var maybeLow = item.reserveStock(command);

        inventoryRepository.save(item);

        maybeLow.ifPresent(eventPublisher::publishEvent);

        return maybeLow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void handle(ReleaseInventoryCommand command) {
        var item = inventoryRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found: " + command.itemId()));

        item.releaseStock(command);

        inventoryRepository.save(item);
    }
}