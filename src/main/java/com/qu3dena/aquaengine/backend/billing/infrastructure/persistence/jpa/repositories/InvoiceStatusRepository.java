package com.qu3dena.aquaengine.backend.billing.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.billing.domain.model.entities.InvoiceStatus;
import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@code InvoiceStatus} entities.
 * <p>
 * This interface extends {@code JpaRepository} to provide standard data access operations for invoice statuses.
 * </p>
 */
@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Long> {

    /**
     * Finds an {@code InvoiceStatus} by its name.
     *
     * @param name the {@code InvoiceStatusType} representing the name of the invoice status.
     * @return an {@code Optional} containing the found {@code InvoiceStatus}, or an empty optional if none found.
     */
    Optional<InvoiceStatus> findByName(InvoiceStatusType name);

    /**
     * Checks if an {@code InvoiceStatus} exists by its name.
     *
     * @param name the {@code InvoiceStatusType} representing the name of the invoice status.
     * @return {@code true} if an invoice status with the given name exists, {@code false} otherwise.
     */
    boolean existsByName(InvoiceStatusType name);
}