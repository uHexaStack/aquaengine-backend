package com.qu3dena.aquaengine.backend.catalog.domain.services;

import com.qu3dena.aquaengine.backend.catalog.domain.model.aggregate.ProductAggregate;
import com.qu3dena.aquaengine.backend.catalog.domain.model.commands.CreateProductCommand;
import com.qu3dena.aquaengine.backend.catalog.domain.model.commands.DeleteProductCommand;
import com.qu3dena.aquaengine.backend.catalog.domain.model.commands.UpdateProductCommand;

import java.util.Optional;

public interface ProductCommandService {
    /**
     * Handles the creation of a new product.
     *
     * @param command the command containing product details
     * @return an Optional containing the created ProductAggregate if successful, or empty if not
     */
    Optional<ProductAggregate>handle(CreateProductCommand command);
    /**
     * Handles the deletion of a product.
     *
     * @param command the command containing the product ID to delete
     * @return an Optional containing the deleted ProductAggregate if successful, or empty if not
     */
    Optional<ProductAggregate>handle(DeleteProductCommand command);
    /**
     * Handles the update of an existing product.
     *
     * @param command the command containing updated product details
     * @return an Optional containing the updated ProductAggregate if successful, or empty if not
     */
    Optional<ProductAggregate>handle(UpdateProductCommand command);
}
