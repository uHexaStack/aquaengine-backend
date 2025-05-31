package com.qu3dena.aquaengine.backend.order.domain.model.entities;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.shared.domain.model.entities.AuditableModel;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "order_lines")
@EqualsAndHashCode(callSuper = true)
public class OrderLine extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private OrderAggregate order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    private int quantity;

    @Embedded
    private Money unitPrice;

    public OrderLine(OrderAggregate order, Long productId, int quantity, Money unitPrice) {

        if (quantity <= 0)
            throw new IllegalArgumentException("Invalid quantity: " + quantity);

        this.order = order;
        this.quantity = quantity;
        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    public Money lineTotal() {
        return unitPrice.multiply(quantity);
    }
}
