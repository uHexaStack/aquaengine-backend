package com.qu3dena.aquaengine.backend.inventory.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.qu3dena.aquaengine.backend.inventory.domain.services.InventoryCommandService;
import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderCancelledEvent;
import com.qu3dena.aquaengine.backend.order.interfaces.acl.OrderContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCancelledEventHandler {

    private final OrderContextFacade orderFacade;
    private final InventoryCommandService inventoryCommandService;

    public OrderCancelledEventHandler(OrderContextFacade orderFacade, InventoryCommandService inventoryCommandService) {
        this.orderFacade = orderFacade;
        this.inventoryCommandService = inventoryCommandService;
    }

    @EventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        Long orderId = event.orderId();

        var productQuantities = orderFacade.getOrderLines(orderId);

            productQuantities.forEach((productId, quantity) -> {
                try {
                    inventoryCommandService.handle(
                            new ReleaseInventoryCommand(productId, quantity)
                    );
                } catch (IllegalArgumentException ex) {
                    System.err.println(
                            "Stock release failed for productId=" + productId +
                                    " in orderId=" + orderId + ": " + ex.getMessage()
                    );
                }
            });
    }
}
