package com.qu3dena.aquaengine.backend.inventory.domain.model.aggregate;

import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.AdjustInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.model.events.StockLowEvent;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Quantity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inventory_items")
@EqualsAndHashCode(callSuper = true)
public class InventoryItemAggregate extends AuditableAbstractAggregateRoot<InventoryItemAggregate> {

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Embedded
    private Quantity availableQuantity;

    public InventoryItemAggregate(Long productId, Quantity availableQuantity) {
        this.productId = productId;
        this.availableQuantity = availableQuantity;
    }

    public static InventoryItemAggregate create(CreateInventoryItemCommand command) {
        if (command.productId() == null)
            throw new IllegalArgumentException("Product id cannot be null");

        if (command.initialQuantity() < 0)
            throw new IllegalArgumentException("Available quantity cannot be negative");

        return new InventoryItemAggregate(
                command.productId(),
                new Quantity(command.initialQuantity())
        );
    }

    public Optional<StockLowEvent> adjustStock(AdjustInventoryCommand command) {

        var newQuantity = availableQuantity.amount() + command.adjustBy();

        if (newQuantity < 0)
            throw new IllegalArgumentException("Insufficient stock to adjust");

        availableQuantity = new Quantity(newQuantity);

        if (availableQuantity.amount() <= 5)
            return Optional.of(new StockLowEvent(this.getId(), productId, availableQuantity.amount()));

        return Optional.empty();
    }

    public Optional<StockLowEvent> reserveStock(ReserveInventoryCommand command) {

        var quantity = command.quantityToReserve();

        if (quantity < 0)
            throw new IllegalArgumentException("Quantity to reserve must be > 0");

        var available = availableQuantity.amount();

        if (available < quantity)
            throw new IllegalArgumentException("Not enough stock to reserve for product " + productId);

        var newQuantity = available - quantity;

        availableQuantity = new Quantity(newQuantity);

        if (newQuantity <= 5)
            return Optional.of(new StockLowEvent(this.getId(), productId, newQuantity));

        return Optional.empty();
    }

    public void releaseStock(ReleaseInventoryCommand command) {
        var quantity = command.quantityToRelease();

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity to release must be > 0");

        var newQuantity = this.availableQuantity.amount() + quantity;

        this.availableQuantity = new Quantity(newQuantity);
    }

    public int getAvailableQuantity() {
        return availableQuantity.amount();
    }
}
