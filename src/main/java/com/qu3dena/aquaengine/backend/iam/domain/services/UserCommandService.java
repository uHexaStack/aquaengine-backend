package com.qu3dena.aquaengine.backend.iam.domain.services;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignInCommand;
import com.qu3dena.aquaengine.backend.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<UserAggregate> handle(SignUpCommand command);
    Optional<ImmutablePair<UserAggregate, String>> handle(SignInCommand command);
}
