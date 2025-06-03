package com.qu3dena.aquaengine.backend.order.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.order.domain.model.commands.CreateOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderLine;
import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.ShippingAddress;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class OrderAggregate extends AuditableAbstractAggregateRoot<OrderAggregate> {

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderLine> lines = new ArrayList<>();

    @Embedded
    private Money total;

    public static OrderAggregate create(CreateOrderCommand command, OrderStatus status) {

        OrderAggregate order = new OrderAggregate();

        order.setUserId(command.userId());
        order.setStatus(status);
        order.setShippingAddress(command.shippingAddress());

        for (CreateOrderCommand.Line line : command.lines()) {
            Money unitPrice = new Money(line.unitPrice(), line.currency());

            OrderLine newLine = new OrderLine(
                    order,
                    line.productId(),
                    line.quantity(),
                    unitPrice
            );

            order.addLine(newLine);
        }

        Money total = order.getLines().stream()
                .map(OrderLine::lineTotal)
                .reduce(new Money(
                        ZERO, command.lines().get(0).currency()), Money::add);

        order.setTotal(total);

        return order;
    }

    private void addLine(OrderLine line) {
        if (line == null)
            throw new IllegalArgumentException("Order line cannot be null");

        line.setOrder(this);
        this.lines.add(line);
    }

    private void removeLine(OrderLine line) {
        if (line == null)
            throw new IllegalArgumentException("Order line cannot be null");

        this.lines.remove(line);

        line.setOrder(null);
    }

}
