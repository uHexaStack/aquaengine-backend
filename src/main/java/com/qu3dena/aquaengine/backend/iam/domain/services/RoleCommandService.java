package com.qu3dena.aquaengine.backend.iam.domain.services;

import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}