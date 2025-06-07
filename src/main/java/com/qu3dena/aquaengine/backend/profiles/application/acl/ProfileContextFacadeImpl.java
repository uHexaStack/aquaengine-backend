package com.qu3dena.aquaengine.backend.profiles.application.acl;

import com.qu3dena.aquaengine.backend.profiles.domain.model.commands.CreateProfileCommand;
import com.qu3dena.aquaengine.backend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.qu3dena.aquaengine.backend.profiles.domain.services.ProfileCommandService;
import com.qu3dena.aquaengine.backend.profiles.domain.services.ProfileQueryService;
import com.qu3dena.aquaengine.backend.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfileContextFacadeImpl(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @Override
    public void createProfile(
            Long userId,
            String firstName,
            String lastName,
            String ruc,
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
                ruc,
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

    @Override
    public String getContactEmailByUserId(Long userId) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(userId))
                .orElseThrow(() ->
                        new IllegalArgumentException("Profile not found for user ID: " + userId)
                ).getContactEmail();
    }

    @Override
    public String getContactPhoneByUserId(Long userId) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(userId))
                .orElseThrow(() ->
                        new IllegalArgumentException("Profile not found for user ID: " + userId)
                ).getContactPhone();
    }
}
