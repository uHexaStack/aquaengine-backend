package com.qu3dena.aquaengine.backend.payment.domain.model.entities;

import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import com.qu3dena.aquaengine.backend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents the status of a payment.
 * <p>
 * This entity is responsible for tracking the current payment status.
 * It extends the {@code AuditableModel} to include auditing information.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_statuses")
@EqualsAndHashCode(callSuper = true)
public class PaymentStatus extends AuditableModel {

    /**
     * The unique identifier of the payment status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The payment status type.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private PaymentStatusType name;

    /**
     * Constructs a new {@code PaymentStatus} with the specified status type.
     *
     * @param name the payment status type
     */
    public PaymentStatus(PaymentStatusType name) {
        this.name = name;
    }

    /**
     * Returns the default payment status.
     *
     * @return a new {@code PaymentStatus} with the default status (PENDING)
     */
    public static PaymentStatus getDefaultStatus() {
        return new PaymentStatus(PaymentStatusType.PENDING);
    }

    /**
     * Converts a string value to a {@code PaymentStatus} entity.
     *
     * @param name the name of the payment status
     * @return a new {@code PaymentStatus} created from the given name
     * @throws IllegalArgumentException if the name does not match any {@code PaymentStatusType}
     */
    public static PaymentStatus toPaymentStatusFromName(String name) {
        return new PaymentStatus(PaymentStatusType.valueOf(name));
    }

    /**
     * Returns the string representation of the payment status type.
     *
     * @return the name of the payment status type
     */
    public String getStringStatus() {
        return this.name.name();
    }

    /**
     * Creates a new {@code PaymentStatus} from a string value.
     *
     * @param name the name of the payment status
     * @return a new {@code PaymentStatus} created from the given name
     * @throws IllegalArgumentException if the name does not match any {@code PaymentStatusType}
     */
    public static PaymentStatus create(String name) {
        return new PaymentStatus(PaymentStatusType.valueOf(name));
    }
}