package com.qu3dena.aquaengine.backend.notification.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.notification.interfaces.acl.NotificationContextFacade;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentFailedEvent;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentFailedEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public PaymentFailedEventHandler(NotificationContextFacade notificationFacade, ProfileContextFacade profileFacade) {
        this.notificationFacade = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onPaymentFailed(PaymentFailedEvent e) {
        String recipient = profileFacade.getContactEmailByUserId(e.orderId());
        String payload   = "Your payment for order " + e.orderId()
                + " has failed. Please check your payment details and try again.";

        notificationFacade.sendNotification("EMAIL", recipient, payload, e.orderId(), null, null);
    }
}
