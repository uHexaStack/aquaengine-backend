package com.qu3dena.aquaengine.backend.billing.domain.model.entities;

import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the status of an invoice.
 * <p>
 * This entity maps the invoice status to a relational database.
 * It uses an {@code InvoiceStatusType} enumeration to ensure valid status names.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_statuses")
public class InvoiceStatus {

    /**
     * The unique identifier for the invoice status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The status name represented as an {@code InvoiceStatusType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private InvoiceStatusType name;

    /**
     * Constructs an {@code InvoiceStatus} with the given status.
     *
     * @param name the status type of the invoice.
     */
    public InvoiceStatus(InvoiceStatusType name) {
        this.name = name;
    }

    /**
     * Factory method to create a new instance of {@code InvoiceStatus} with the specified status type.
     *
     * @param name the status type of the invoice.
     * @return a new instance of {@code InvoiceStatus}.
     */
    public static InvoiceStatus create(InvoiceStatusType name) {
        return new InvoiceStatus(name);
    }
}