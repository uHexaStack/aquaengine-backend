package com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.billing.domain.model.aggregates.InvoiceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@code InvoiceAggregate} entities.
 * <p>
 * This interface extends {@code JpaRepository} to provide standard data access operations.
 * </p>
 */
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceAggregate, Long> {

    /**
     * Finds an {@code InvoiceAggregate} by its associated order identifier.
     *
     * @param orderId the unique identifier of the order.
     * @return an {@code Optional} containing the found {@code InvoiceAggregate}, or an empty optional if none found.
     */
    Optional<InvoiceAggregate> findByOrderId(Long orderId);
}