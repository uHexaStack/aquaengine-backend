package com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing PaymentAggregate data.
 * Provides methods to retrieve PaymentAggregate instances by order identifier.
 */
public interface PaymentRepository extends JpaRepository<PaymentAggregate, Long> {

    /**
     * Finds a PaymentAggregate by its order identifier.
     *
     * @param orderId the unique order identifier
     * @return an Optional containing the PaymentAggregate if found; otherwise, an empty Optional
     */
    Optional<PaymentAggregate> findByOrderId(Long orderId);

    /**
     * Finds all PaymentAggregates associated with a given order identifier.
     *
     * @param orderId the unique order identifier
     * @return a list of PaymentAggregates for the specified order identifier
     */
    List<PaymentAggregate> findAllByOrderId(Long orderId);
}