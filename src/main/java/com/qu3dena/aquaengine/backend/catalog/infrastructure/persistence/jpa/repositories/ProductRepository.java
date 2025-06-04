package com.qu3dena.aquaengine.backend.catalog.infrastructure.persistence.jpa.repositories;


import com.qu3dena.aquaengine.backend.catalog.domain.model.aggregate.ProductAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductAggregate, Long> {

    Optional<ProductAggregate> findById(Long id);

    Optional<ProductAggregate> findByName(String name);

    List<ProductAggregate> findAll();
}
