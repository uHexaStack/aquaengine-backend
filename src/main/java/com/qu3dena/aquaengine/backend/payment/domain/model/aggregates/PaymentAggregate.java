package com.qu3dena.aquaengine.backend.payment.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.entities.PaymentStatus;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentProcessedEvent;
import com.qu3dena.aquaengine.backend.payment.domain.model.events.PaymentRefundedEvent;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentMethod;
import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class PaymentAggregate extends AuditableAbstractAggregateRoot<PaymentAggregate> {

    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @Embedded
    private Money amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private PaymentStatus status;

    @Embedded
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    public PaymentAggregate(
            Long orderId,
            Money amount,
            PaymentStatus status,
            PaymentMethod paymentMethod,
            Instant transactionDate
    ) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
    }

    public static PaymentAggregate create(ProcessPaymentCommand command, PaymentStatus pendingStatus) {
        return new PaymentAggregate(
                command.orderId(),
                new Money(command.amount(), command.currency()),
                pendingStatus,
                new PaymentMethod(command.method()),
                Instant.now()
        );
    }

    public Optional<PaymentStatusType> process() {
        if (amount.amount().signum() <= 0) {
            return Optional.of(PaymentStatusType.FAILED);
        }
        return Optional.of(PaymentStatusType.COMPLETED);
    }

    public PaymentStatusType refund() {
        return PaymentStatusType.REFUNDED;
    }
}