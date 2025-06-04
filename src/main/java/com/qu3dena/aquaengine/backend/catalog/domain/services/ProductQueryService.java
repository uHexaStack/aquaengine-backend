package com.qu3dena.aquaengine.backend.catalog.domain.services;

import com.qu3dena.aquaengine.backend.catalog.domain.model.aggregate.ProductAggregate;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the ProductAggregate if found, or empty if not found
     */
    Optional<ProductAggregate> findById(Long id);
    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product
     * @return an Optional containing the ProductAggregate if found, or empty if not found
     */
    Optional<ProductAggregate> findByName(String name);
    /**
     * Retrieves all products.
     *
     * @return a List of ProductAggregate containing all products
     */
    List<ProductAggregate> findAll();
}
