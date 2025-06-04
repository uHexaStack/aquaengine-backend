package com.qu3dena.aquaengine.backend.payment.application.acl;

import com.qu3dena.aquaengine.backend.payment.domain.model.aggregates.PaymentAggregate;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.commands.RefundPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.queries.GetPaymentByIdQuery;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentCommandService;
import com.qu3dena.aquaengine.backend.payment.domain.services.PaymentQueryService;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentRepository;
import com.qu3dena.aquaengine.backend.payment.infrastructure.persistence.jpa.repositories.PaymentStatusRepository;
import com.qu3dena.aquaengine.backend.payment.interfaces.acl.PaymentContextFacade;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@code PaymentContextFacade} interface.
 * The method documentation in this class is inherited from the interface.
 */
@Service
public class PaymentContextFacadeImpl implements PaymentContextFacade {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    /**
     * Constructs a new PaymentContextFacadeImpl.
     *
     * @param paymentCommandService   the service handling payment-related commands
     * @param paymentQueryService     the service handling payment-related queries
     * @param paymentRepository       the repository for accessing payment aggregates
     * @param paymentStatusRepository the repository for accessing payment statuses
     */
    public PaymentContextFacadeImpl(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService, PaymentRepository paymentRepository, PaymentStatusRepository paymentStatusRepository) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
        this.paymentRepository = paymentRepository;
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long processPayment(Long orderId, BigDecimal amount, String currency, String method) {
        if (orderId == null || amount == null || method == null || currency == null)
            return 0L;

        var maybeStatus = paymentStatusRepository.findByName(PaymentStatusType.PENDING);
        if (maybeStatus.isEmpty())
            return 0L;

        var command = new ProcessPaymentCommand(orderId, amount, currency, method);
        var maybePayment = paymentCommandService.handle(command);
        return maybePayment.map(AuditableAbstractAggregateRoot::getId).orElse(0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean refundPayment(Long paymentId) {
        if (paymentId == null)
            return false;

        var command = new RefundPaymentCommand(paymentId);
        var result = paymentCommandService.handle(command);
        return result.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentStatus(Long paymentId) {
        if (paymentId == null)
            return null;

        var maybe = paymentQueryService.handle(new GetPaymentByIdQuery(paymentId));

        return maybe
                .map(p -> p.getStatus().getStringStatus()).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getPaymentsByOrderId(Long orderId) {
        if (orderId == null)
            return List.of();

        return paymentRepository.findAllByOrderId(orderId)
                .stream()
                .map(PaymentAggregate::getId)
                .collect(Collectors.toList());
    }
}