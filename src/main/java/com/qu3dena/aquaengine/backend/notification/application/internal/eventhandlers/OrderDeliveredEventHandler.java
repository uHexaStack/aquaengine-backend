package com.qu3dena.aquaengine.backend.notification.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.notification.interfaces.acl.NotificationContextFacade;
import com.qu3dena.aquaengine.backend.order.domain.model.events.OrderDeliveredEvent;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderDeliveredEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public OrderDeliveredEventHandler(NotificationContextFacade notificationFacade, ProfileContextFacade profileFacade) {
        this.notificationFacade = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onOrderDelivered(OrderDeliveredEvent e) {
        String recipient = profileFacade.getContactPhoneByUserId(e.userId());
        String payload   = "Your order with ID " + e.orderId() + " has been delivered successfully.";
        notificationFacade.sendNotification("SMS", recipient, payload, e.orderId(), null, null);
    }
}