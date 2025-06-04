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

/**
 * Represents the aggregate root for a payment in the domain.
 * Contains data and operations for processing and refunding a payment.
 */
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

    /**
     * Constructs a PaymentAggregate with the specified details.
     *
     * @param orderId         the unique order identifier
     * @param amount          the monetary amount of the payment
     * @param status          the current status of the payment
     * @param paymentMethod   the payment method used for the transaction
     * @param transactionDate the date and time when the transaction occurred
     */
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

    /**
     * Creates a new instance of PaymentAggregate from the given command and pending status.
     *
     * @param command       the command containing payment creation details
     * @param pendingStatus the initial pending status of the payment
     * @return a new PaymentAggregate instance
     */
    public static PaymentAggregate create(ProcessPaymentCommand command, PaymentStatus pendingStatus) {
        return new PaymentAggregate(
                command.orderId(),
                new Money(command.amount(), command.currency()),
                pendingStatus,
                new PaymentMethod(command.method()),
                Instant.now()
        );
    }

    /**
     * Processes the payment.
     * If the payment amount is less than or equal to zero, the payment fails.
     *
     * @return an Optional containing PaymentProcessedEvent if processing succeeds; otherwise, an empty Optional
     */
    public Optional<PaymentProcessedEvent> process() {
        if (amount.amount().signum() <= 0) {
            this.status = new PaymentStatus(PaymentStatusType.FAILED);
            return Optional.empty();
        }
        this.status = new PaymentStatus(PaymentStatusType.COMPLETED);
        return Optional.of(new PaymentProcessedEvent(this.getId(), this.orderId));
    }

    /**
     * Refunds the payment by updating its status.
     *
     * @return a PaymentRefundedEvent representing the refund event
     */
    public PaymentRefundedEvent refund() {
        this.status = new PaymentStatus(PaymentStatusType.REFUNDED);
        return new PaymentRefundedEvent(this.getId(), this.orderId);
    }
}