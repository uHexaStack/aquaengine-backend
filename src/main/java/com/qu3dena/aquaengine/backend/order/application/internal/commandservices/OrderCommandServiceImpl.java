package com.qu3dena.aquaengine.backend.order.application.internal.commandservices;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.*;
import com.qu3dena.aquaengine.backend.order.domain.model.events.*;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderCommandService;
import com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher events;

    public OrderCommandServiceImpl(OrderRepository orderRepository, ApplicationEventPublisher events) {
        this.events = events;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Optional<OrderAggregate> handle(CreateOrderCommand command) {
        var order = OrderAggregate.create(command);

        var saved = orderRepository.save(order);

        events.publishEvent(new OrderCreatedEvent(
                saved.getId(),
                command.userId()
        ));

        return Optional.of(order);
    }

    @Override
    public void handle(ConfirmOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.getStatus().confirm();

        orderRepository.save(order);

        events.publishEvent(new OrderConfirmedEvent(
                command.orderId(),
                order.getUserId()
        ));
    }

    @Override
    public void handle(CancelOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.getStatus().cancel();

        orderRepository.save(order);

        events.publishEvent(new OrderCancelledEvent(
                command.orderId(),
                order.getUserId()
        ));

    }

    @Override
    public void handle(ShipOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.getStatus().ship();

        orderRepository.save(order);

        events.publishEvent(new OrderShippedEvent(
                command.orderId(),
                order.getUserId()
        ));

    }

    @Override
    public void handle(DeliverOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.getStatus().deliver();

        orderRepository.save(order);

        events.publishEvent(new OrderDeliveredEvent(
                command.orderId(),
                order.getUserId()
        ));

    }
}
