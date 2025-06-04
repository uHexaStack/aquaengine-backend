package com.qu3dena.aquaengine.backend.payment.application.internal.queryservices;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentByIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentsByOrderIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentQueryService;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@code PaymentQueryService}.
 * Provides methods to handle queries for retrieving payment aggregates
 * by payment identifier and order identifier.
 */
@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    /**
     * Constructs a new {@code PaymentQueryServiceImpl}.
     *
     * @param paymentRepository the repository for accessing payment aggregates
     */
    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Processes the query to retrieve a {@code PaymentAggregate} by its identifier.
     *
     * @param query a {@code GetPaymentByIdQuery} containing the payment identifier
     * @return an {@code Optional} containing the corresponding {@code PaymentAggregate} if found; otherwise, an empty {@code Optional}
     */
    @Override
    public Optional<PaymentAggregate> handle(GetPaymentByIdQuery query) {
        return paymentRepository.findById(query.paymentId());
    }

    /**
     * Processes the query to retrieve a list of {@code PaymentAggregate} by order identifier.
     *
     * @param query a {@code GetPaymentsByOrderIdQuery} containing the order identifier
     * @return a {@code List} of {@code PaymentAggregate} instances associated with the given order
     */
    @Override
    public List<PaymentAggregate> handle(GetPaymentsByOrderIdQuery query) {
        return paymentRepository.findAllByOrderId(query.orderId());
    }
}