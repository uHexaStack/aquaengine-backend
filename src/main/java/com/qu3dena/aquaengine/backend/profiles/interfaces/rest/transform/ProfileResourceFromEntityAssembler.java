package com.qu3dena.aquaengine.backend.profiles.interfaces.rest.transform;

import com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates.ProfileAggregate;
import com.qu3dena.aquaengine.backend.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResourceFromEntity(ProfileAggregate entity) {
        return new ProfileResource(
                entity.getUserId(),
                entity.getFullName(),
                entity.getContactEmail(),
                entity.getContactPhone(),
                entity.getCompanyInfo()
        );
    }
}
