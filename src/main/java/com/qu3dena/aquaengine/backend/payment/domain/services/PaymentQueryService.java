package com.qu3dena.aquaengine.backend.payment.domain.services;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentByIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentsByOrderIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to handle queries related to payment operations.
 */
public interface PaymentQueryService {

    /**
     * Handles the query to retrieve a PaymentAggregate by its unique identifier.
     *
     * @param query the query containing the payment identifier
     * @return an Optional containing the PaymentAggregate if found; otherwise, an empty Optional
     */
    Optional<PaymentAggregate> handle(GetPaymentByIdQuery query);

    /**
     * Handles the query to retrieve a list of PaymentAggregates by the associated order identifier.
     *
     * @param query the query containing the order identifier
     * @return a list of PaymentAggregates matching the provided order identifier
     */
    List<PaymentAggregate> handle(GetPaymentsByOrderIdQuery query);
}