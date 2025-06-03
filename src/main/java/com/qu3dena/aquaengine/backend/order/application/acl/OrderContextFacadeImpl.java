package com.qu3dena.aquaengine.backend.order.application.acl;

import com.qu3dena.aquaengine.backend.order.domain.model.aggregates.OrderAggregate;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.CancelOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.ConfirmOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.CreateOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.DeliverOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.commands.ShipOrderCommand;
import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderLine;
import com.qu3dena.aquaengine.backend.order.domain.model.entities.OrderStatus;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetAllOrderStatusTypeQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrderByIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.queries.GetOrdersByUserIdQuery;
import com.qu3dena.aquaengine.backend.order.domain.model.valueobjects.ShippingAddress;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderCommandService;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderQueryService;
import com.qu3dena.aquaengine.backend.order.domain.services.OrderStatusQueryService;
import com.qu3dena.aquaengine.backend.order.infrastructure.persistence.jpa.repositories.OrderRepository;
import com.qu3dena.aquaengine.backend.order.interfaces.acl.OrderContextFacade;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderContextFacadeImpl implements OrderContextFacade {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;
    private final OrderStatusQueryService orderStatusQueryService;
    private final OrderRepository orderRepository;

    public OrderContextFacadeImpl(
            OrderCommandService orderCommandService,
            OrderQueryService orderQueryService,
            OrderStatusQueryService orderStatusQueryService,
            OrderRepository orderRepository) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
        this.orderStatusQueryService = orderStatusQueryService;
        this.orderRepository = orderRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long createOrder(Long userId,
                            String street, String city,
                            String postalCode, String country, List<Long> productIds,
                            List<Integer> quantities, List<Double> unitPrices, List<String> currencies) {

        if (userId == null
                || street == null || city == null || postalCode == null || country == null
                || productIds == null || quantities == null || unitPrices == null || currencies == null
                || productIds.size() != quantities.size()
                || quantities.size() != unitPrices.size()
                || unitPrices.size() != currencies.size()
        ) return 0L;

        ShippingAddress shippingAddress;

        try {
            shippingAddress = new ShippingAddress(street, city, postalCode, country);
        } catch (
                IllegalArgumentException ex) {
            return 0L;
        }

        List<CreateOrderCommand.Line> lines = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            String currency = currencies.get(i);
            Integer quantity = quantities.get(i);
            BigDecimal unitPrice = BigDecimal.valueOf(unitPrices.get(i));

            if (productId == null || quantity == null || currency == null)
                return 0L;

            lines.add(new CreateOrderCommand.Line(productId, quantity, unitPrice, currency));
        }

        CreateOrderCommand command = new CreateOrderCommand(userId, shippingAddress, lines);
        Optional<OrderAggregate> maybeOrder = orderCommandService.handle(command);

        return maybeOrder.map(OrderAggregate::getId).orElse(0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmOrder(Long orderId) {
        if (orderId == null)
            return false;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return false;

        var command = new ConfirmOrderCommand(orderId);
        orderCommandService.handle(command);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cancelOrder(Long orderId) {
        if (orderId == null)
            return false;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return false;

        var command = new CancelOrderCommand(orderId);
        orderCommandService.handle(command);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shipOrder(Long orderId) {
        if (orderId == null)
            return false;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return false;

        var command = new ShipOrderCommand(orderId);
        orderCommandService.handle(command);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deliverOrder(Long orderId) {
        if (orderId == null)
            return false;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return false;

        var command = new DeliverOrderCommand(orderId);
        orderCommandService.handle(command);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderStatus(Long orderId) {
        if (orderId == null)
            return null;

        var maybe = findOrderById(orderId);

        return maybe.map(orderAggregate ->
                orderAggregate.getStatus().getStringStatus()).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getOrderTotal(Long orderId) {
        if (orderId == null)
            return -1.0;

        var maybe = findOrderById(orderId);

        return maybe.map(orderAggregate ->
                orderAggregate.getTotal().amount().doubleValue()).orElse(-1.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getShippingAddress(Long orderId) {
        if (orderId == null)
            return null;

        var maybe = findOrderById(orderId);

        return maybe.map(orderAggregate ->
                orderAggregate.getShippingAddress().toString()).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getOrderProductIds(Long orderId) {
        List<Long> result = new ArrayList<>();

        if (orderId == null)
            return result;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return result;

        for (OrderLine line : maybe.get().getLines()) {
            result.add(line.getProductId());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getOrderQuantities(Long orderId) {
        List<Integer> result = new ArrayList<>();

        if (orderId == null)
            return result;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return result;

        for (OrderLine line : maybe.get().getLines()) {
            result.add(line.getQuantity());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getOrderUnitPrices(Long orderId) {
        List<Double> result = new ArrayList<>();

        if (orderId == null)
            return result;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return result;

        for (OrderLine line : maybe.get().getLines()) {
            result.add(line.getUnitPrice().amount().doubleValue());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getOrderCurrencies(Long orderId) {
        List<String> result = new ArrayList<>();

        if (orderId == null)
            return result;

        var maybe = findOrderById(orderId);

        if (maybe.isEmpty())
            return result;

        for (OrderLine line : maybe.get().getLines()) {
            result.add(line.getUnitPrice().currency());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getOrderIdsByUserId(Long userId) {
        List<Long> result = new ArrayList<>();

        if (userId == null)
            return result;

        var query = new GetOrdersByUserIdQuery(userId);

        var orders = orderQueryService.handle(query);

        for (OrderAggregate order : orders) {
            result.add(order.getId());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllOrderStatuses() {
        var query = new GetAllOrderStatusTypeQuery();

        var statuses = orderStatusQueryService.handle(query);

        return statuses.stream()
                .map(OrderStatus::getStringStatus)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Integer> getOrderLines(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        return order.getLines()
                .stream()
                .collect(Collectors.toMap(
                        OrderLine::getProductId,
                        OrderLine::getQuantity
                ));
    }


    /**
     * Helper method to retrieve an OrderAggregate by ID.
     *
     * @param orderId the ID of the order.
     * @return an Optional containing the OrderAggregate if found.
     */
    private Optional<OrderAggregate> findOrderById(Long orderId) {
        if (orderId == null)
            return Optional.empty();

        return orderQueryService.handle(new GetOrderByIdQuery(orderId));
    }
}