package com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.payment.domain.model.entities.PaymentStatus;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@code PaymentStatus} entities.
 * Provides methods to perform CRUD operations on payment status data.
 */
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    /**
     * Finds a {@code PaymentStatus} by its name.
     *
     * @param name the payment status type to search for
     * @return an {@code Optional} containing the matching {@code PaymentStatus} if found; otherwise, an empty {@code Optional}
     */
    Optional<PaymentStatus> findByName(PaymentStatusType name);

    /**
     * Checks if a {@code PaymentStatus} exists by its name.
     *
     * @param name the payment status type to check
     * @return {@code true} if a {@code PaymentStatus} with the given name exists; otherwise, {@code false}
     */
    boolean existsByName(PaymentStatusType name);
}