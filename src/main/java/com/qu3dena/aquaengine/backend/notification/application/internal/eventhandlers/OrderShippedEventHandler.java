package com.qu3dena.aquaengine.backend.notification.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.notification.interfaces.acl.NotificationContextFacade;
import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderShippedEvent;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderShippedEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public OrderShippedEventHandler(NotificationContextFacade notificationFacade,
                                   ProfileContextFacade profileFacade) {
        this.notificationFacade        = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onOrderShipped(OrderShippedEvent e) {

        String recipient = profileFacade.getContactEmailByUserId(e.userId());

        String payload   = "Your order with ID " + e.orderId() + " has been shipped. Thank you for shopping with us!";

        notificationFacade.sendNotification("EMAIL", recipient, payload, e.orderId(), null, null);
    }
}