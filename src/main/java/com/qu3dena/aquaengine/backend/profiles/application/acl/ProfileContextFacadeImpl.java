package com.qu3dena.aquaengine.backend.profiles.application.acl;

import com.qu3dena.aquaengine.backend.profiles.domain.model.commands.CreateProfileCommand;
import com.qu3dena.aquaengine.backend.profiles.domain.services.ProfileCommandService;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {

    private final ProfileCommandService profileCommandService;

    public ProfileContextFacadeImpl(ProfileCommandService profileCommandService) {
        this.profileCommandService = profileCommandService;
    }

    @Override
    public void createProfile(
            Long userId,
            String firstName,
            String lastName,
            String contactEmail,
            String contactPhone,
            String companyName,
            String companyStreet,
            String companyCity,
            String postalCode,
            String companyCountry
    ) {
        var command = new CreateProfileCommand(
                userId,
                firstName,
                lastName,
                contactEmail,
                contactPhone,
                companyName,
                companyStreet,
                companyCity,
                postalCode,
                companyCountry
        );

        profileCommandService.handle(command);
    }
}
