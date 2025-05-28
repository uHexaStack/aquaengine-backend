package com.qu3dena.aquaengine.backend.iam.domain.services;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetAllUsersQuery;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.qu3dena.aquaengine.backend.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<UserAggregate> handle(GetAllUsersQuery query);
    Optional<UserAggregate> handle(GetUserByIdQuery query);
    Optional<UserAggregate> handle(GetUserByUsernameQuery query);
}