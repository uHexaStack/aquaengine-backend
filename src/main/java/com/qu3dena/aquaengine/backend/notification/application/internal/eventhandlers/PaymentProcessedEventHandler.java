package com.qu3dena.aquaengine.backend.notification.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.notification.domain.model.commands.SendNotificationCommand;
import com.qu3dena.aquaengine.backend.notification.domain.services.NotificationCommandService;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentProcessedEvent;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessedEventHandler {

    private final NotificationCommandService commandService;
    private final ProfileContextFacade profileFacade;

    public PaymentProcessedEventHandler(
            NotificationCommandService commandService,
            ProfileContextFacade       profileFacade
    ) {
        this.commandService = commandService;
        this.profileFacade   = profileFacade;
    }

    @EventListener
    public void onPaymentProcessed(PaymentProcessedEvent e) {
        String email = profileFacade.getContactEmailByUserId(e.userId());

        String payload = String.format(
                "Tu pago (ID: %d) para la orden #%d se ha procesado correctamente.",
                e.paymentId(), e.orderId()
        );

        commandService.handle(new SendNotificationCommand(
                "EMAIL", email, payload,
                e.orderId(), null, e.paymentId()
        ));
    }
}