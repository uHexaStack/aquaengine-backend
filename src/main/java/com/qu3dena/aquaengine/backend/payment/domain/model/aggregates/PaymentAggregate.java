package com.qu3dena.aquaengine.backend.payment.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.payment.domain.model.commands.ProcessPaymentCommand;
import com.qu3dena.aquaengine.backend.payment.domain.model.entities.PaymentStatus;
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
 * Represents an aggregate for managing payment operations.
 * <p>
 * This aggregate encapsulates the payment order details, amount, payment method, status, and transaction date.
 * It provides methods to process and refund payments.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class PaymentAggregate extends AuditableAbstractAggregateRoot<PaymentAggregate> {

    /**
     * The unique identifier for the order associated with the payment.
     */
    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    /**
     * The monetary amount associated with the payment.
     */
    @Embedded
    private Money amount;

    /**
     * The current status of the payment.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private PaymentStatus status;

    /**
     * The payment method used for the payment.
     */
    @Embedded
    private PaymentMethod paymentMethod;

    /**
     * The transaction date of the payment.
     */
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    /**
     * Constructs a new PaymentAggregate.
     *
     * @param orderId         the unique identifier of the order
     * @param amount          the monetary amount of the payment
     * @param status          the current payment status
     * @param paymentMethod   the method used for the payment
     * @param transactionDate the transaction date of the payment
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
     * Creates a new PaymentAggregate instance based on the provided command and pending status.
     *
     * @param command       the command containing payment processing details
     * @param pendingStatus the initial pending payment status
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
     * Processes the payment based on the payment amount.
     * <p>
     * If the amount is non-positive, the payment is marked as FAILED;
     * otherwise, it is marked as COMPLETED.
     * </p>
     *
     * @return an {@link Optional} containing the next {@link PaymentStatusType} after processing
     */
    public Optional<PaymentStatusType> process() {
        if (amount.amount().signum() <= 0) {
            return Optional.of(PaymentStatusType.FAILED);
        }
        return Optional.of(PaymentStatusType.COMPLETED);
    }

    /**
     * Computes the refund payment status.
     *
     * @return the {@link PaymentStatusType} for a refunded payment
     */
    public PaymentStatusType refund() {
        return PaymentStatusType.REFUNDED;
    }
}