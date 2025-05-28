package com.qu3dena.aquaengine.backend.iam.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.iam.domain.model.aggregates.UserAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAggregate, Long> {

    Optional<UserAggregate> findByUsername(String username);

    boolean existsByUsername(String username);
}