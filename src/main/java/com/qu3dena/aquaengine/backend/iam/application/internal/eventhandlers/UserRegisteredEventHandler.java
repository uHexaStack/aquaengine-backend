package com.qu3dena.aquaengine.backend.iam.application.internal.eventhandlers;

import com.qu3dena.aquaengine.backend.iam.domain.model.events.UserRegisteredEvent;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredEventHandler {

    private final ProfileContextFacade profileFacade;

    public UserRegisteredEventHandler(ProfileContextFacade profileFacade) {
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onUserRegistered(UserRegisteredEvent event) {
        profileFacade.createProfile(
                event.userId(),
                event.firstName(),
                event.lastName(),
                event.contactEmail(),
                event.contactPhone(),
                event.companyName(),
                event.companyStreet(),
                event.companyCity(),
                event.postalCode(),
                event.companyCountry()
        );
    }
}
