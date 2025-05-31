package com.qu3dena.aquaengine.backend.order.domain.model.entities;

import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.OrderStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_statuses")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private OrderStatusType name;

    public OrderStatus(OrderStatusType name) {
        this.name = name;
    }

    public static OrderStatus getDefaultStatus() {
        return new OrderStatus(OrderStatusType.CREATED);
    }

    public static OrderStatus toOrderStatusFromName(String name) {
        return new OrderStatus(OrderStatusType.valueOf(name));
    }

    public static Set<OrderStatus> validateOrderStatus(Set<OrderStatus> orderStatuses) {
        return orderStatuses == null || orderStatuses.isEmpty() ? Set.of(getDefaultStatus()) : orderStatuses;
    }

    public String getStringStatus() {
        return this.name.name();
    }

    public static OrderStatus create(String name) {
        return new OrderStatus(OrderStatusType.valueOf(name));
    }

    public void confirm() {
        this.name = OrderStatusType.CONFIRMED;
    }
    public void cancel() {
        this.name = OrderStatusType.CANCELLED;
    }
    public void ship() {
        this.name = OrderStatusType.SHIPPED;
    }
    public void deliver() {
        this.name = OrderStatusType.DELIVERED;
    }
}
