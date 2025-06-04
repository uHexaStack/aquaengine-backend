package com.qu3dena.aquaengine.backend.payment.domain.model.entities;

import com.qu3dena.aquaengine.backend.payment.domain.model.valueobjects.PaymentStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the status of a payment.
 * <p>
 * This entity tracks the current status of a payment and extends the AuditableModel
 * to include created and modified auditing fields.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_statuses")
public class PaymentStatus {

    /**
     * The unique identifier of the payment status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the payment status.
     * <p>
     * It is stored as a string value in the database and must be unique.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private PaymentStatusType name;

    /**
     * Constructs a new PaymentStatus with the specified status type.
     *
     * @param name the payment status type
     */
    public PaymentStatus(PaymentStatusType name) {
        this.name = name;
    }

    /**
     * Returns the default payment status.
     *
     * @return a new PaymentStatus instance with the default type (PENDING)
     */
    public static PaymentStatus getDefaultStatus() {
        return new PaymentStatus(PaymentStatusType.PENDING);
    }

    /**
     * Converts a string value to a PaymentStatus entity.
     *
     * @param name the name of the payment status
     * @return a new PaymentStatus instance created from the given name
     * @throws IllegalArgumentException if the provided name does not match any PaymentStatusType
     */
    public static PaymentStatus toPaymentStatusFromName(String name) {
        return new PaymentStatus(PaymentStatusType.valueOf(name));
    }

    /**
     * Returns the string representation of the payment status type.
     *
     * @return the name of the payment status type as a string
     */
    public String getStringStatus() {
        return this.name.name();
    }

    /**
     * Creates a new PaymentStatus from a string value.
     *
     * @param name the name of the payment status
     * @return a new PaymentStatus instance created from the given string
     * @throws IllegalArgumentException if the provided name does not match any PaymentStatusType
     */
    public static PaymentStatus create(String name) {
        return new PaymentStatus(PaymentStatusType.valueOf(name));
    }
}