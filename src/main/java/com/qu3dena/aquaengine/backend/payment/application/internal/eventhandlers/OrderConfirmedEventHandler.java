package com.qu3dena.aquaengine.backend.payment.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderConfirmedEvent;
import com.qu3dena.aquaengine.backend.order.interfaces.acl.OrderContextFacade;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentCommandService;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentStatusRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Handles {@code OrderConfirmedEvent} events and processes payments accordingly.
 */
@Service
public class OrderConfirmedEventHandler {

    private final PaymentCommandService paymentCommandService;
    private final OrderContextFacade orderContextFacade;

    /**
     * Constructs a new {@code OrderConfirmedEventHandler}.
     *
     * @param paymentCommandService   the service used to handle payment commands
     * @param orderContextFacade      the facade to retrieve order related information
     */
    public OrderConfirmedEventHandler(
            PaymentCommandService paymentCommandService,
            OrderContextFacade orderContextFacade) {
        this.paymentCommandService = paymentCommandService;
        this.orderContextFacade = orderContextFacade;
    }

    /**
     * Processes the {@code OrderConfirmedEvent} by retrieving the total amount of the order,
     * creating a {@code ProcessPaymentCommand}, and passing it to the payment command service.
     *
     * @param event the order confirmed event
     */
    @EventListener
    public void onOrderConfirmed(OrderConfirmedEvent event) {
        Long userId = event.userId();
        Long orderId = event.orderId();

        BigDecimal totalAmount = BigDecimal
                .valueOf(orderContextFacade.getOrderTotal(orderId));

        String currency = "USD";
        String method = "CREDIT_CARD";

        var command = new ProcessPaymentCommand(userId, orderId, totalAmount, currency, method);

        paymentCommandService.handle(command);
    }
}