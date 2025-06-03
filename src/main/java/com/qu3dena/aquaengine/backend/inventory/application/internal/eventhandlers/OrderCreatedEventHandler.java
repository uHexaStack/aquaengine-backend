package com.qu3dena.aquaengine.backend.inventory.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReserveInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderCreatedEvent;
import com.qu3dena.aquaengine.backend.order.interfaces.acl.OrderContextFacade;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler for processing order created events.
 * <p>
 * When a new order is created, this handler retrieves the order lines and
 * attempts to reserve the corresponding product quantities in the inventory.
 * If the reservation results in a low stock condition, an event is published.
 * </p>
 */
@Service
public class OrderCreatedEventHandler {

    private final OrderContextFacade orderFacade;
    private final InventoryCommandService inventoryCommandService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructs a new OrderCreatedEventHandler with the required dependencies.
     *
     * @param orderFacade                the facade for accessing order details
     * @param inventoryCommandService    the service handling inventory commands
     * @param eventPublisher             the publisher for application events
     */
    public OrderCreatedEventHandler(OrderContextFacade orderFacade, InventoryCommandService inventoryCommandService, ApplicationEventPublisher eventPublisher) {
        this.orderFacade = orderFacade;
        this.inventoryCommandService = inventoryCommandService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the OrderCreatedEvent.
     * <p>
     * Retrieves the product quantities from the order context and attempts to reserve
     * inventory for each product. If a reservation leads to a low stock condition,
     * the corresponding event is published. In case of any errors during reservation,
     * an error message is printed.
     * </p>
     *
     * @param event the OrderCreatedEvent containing order details
     */
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        Long orderId = event.orderId();

        var productQuantities = orderFacade.getOrderLines(orderId);

        productQuantities.forEach((productId, quantity) -> {
            try {
                var maybeLow = inventoryCommandService.handle(
                        new ReserveInventoryCommand(productId, quantity)
                );
                maybeLow.ifPresent(eventPublisher::publishEvent);
            } catch (IllegalArgumentException ex) {
                System.err.println(
                        "Inventory reservation failed for productId=" + productId +
                        " in orderId" + orderId + ": " + ex.getMessage()
                );
            }
        });
    }
}