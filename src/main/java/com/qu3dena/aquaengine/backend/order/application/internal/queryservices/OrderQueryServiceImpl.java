package com.qu3dena.aquaengine.backend.order.application.internal.queryservices;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrderByIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrdersByUserIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderQueryService;
import com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderAggregate> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.orderId());
    }

    @Override
    public List<OrderAggregate> handle(GetOrdersByUserIdQuery query) {
        return orderRepository.findByUserId(query.userId());
    }
}
