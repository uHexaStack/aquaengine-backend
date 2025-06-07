package com.qu3dena.aquaengine.backend.billing.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.billing.domain.model.commands.IssueInvoiceCommand;
import com.qu3dena.aquaengine.backend.billing.domain.model.entities.InvoiceStatus;
import com.qu3dena.aquaengine.backend.billing.domain.model.valueobjects.InvoiceNumber;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents an invoice aggregate that holds invoice data along with auditable properties.
 * <p>
 * This aggregate encapsulates all information related to an invoice,
 * including order id, invoice number, amount, status and the issuance timestamp.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "invoices")
@EqualsAndHashCode(callSuper = true)
public class InvoiceAggregate extends AuditableAbstractAggregateRoot<InvoiceAggregate> {

    /**
     * The unique identifier of the associated order.
     */
    @Column(nullable = false)
    private Long orderId;

    /**
     * The invoice number encapsulated as a value object.
     */
    @Embedded
    private InvoiceNumber invoiceNumber;

    /**
     * The monetary value of the invoice encapsulated as a Money value object.
     */
    @Embedded
    private Money amount;

    /**
     * The status of the invoice.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private InvoiceStatus status;

    /**
     * The timestamp of when the invoice was issued.
     */
    @Column(nullable = false)
    private Instant issuedAt;

    /**
     * Constructs an InvoiceAggregate with all required properties.
     *
     * @param orderId       the order identifier.
     * @param invoiceNumber the invoice number.
     * @param amount        the invoice monetary amount.
     * @param status        the invoice status.
     * @param issuedAt      the issuance timestamp.
     */
    public InvoiceAggregate(Long orderId, InvoiceNumber invoiceNumber, Money amount, InvoiceStatus status, Instant issuedAt) {
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.status = status;
        this.issuedAt = issuedAt;
    }

    /**
     * Creates an InvoiceAggregate instance using the given issue command, status and invoice number.
     *
     * @param command the invoice issuance command.
     * @param status  the invoice status.
     * @param number  the generated invoice number.
     * @return a new instance of InvoiceAggregate.
     */
    public static InvoiceAggregate create(IssueInvoiceCommand command,
                                          InvoiceStatus status,
                                          InvoiceNumber number) {
        return new InvoiceAggregate(
                command.orderId(),
                number,
                new Money(command.amount(), command.currency()),
                status,
                Instant.now()
        );
    }

    /**
     * Retrieves the invoice number as a string.
     *
     * @return the invoice number value if present, otherwise {@code null}.
     */
    public String getInvoiceNumber() {
        return invoiceNumber != null ? invoiceNumber.value() : null;
    }

    /**
     * Retrieves the invoice amount.
     *
     * @return the invoice monetary amount as a BigDecimal, or {@code BigDecimal.ZERO} if not set.
     */
    public BigDecimal getAmount() {
        return amount != null ? amount.amount() : BigDecimal.ZERO;
    }

    /**
     * Retrieves the currency of the invoice.
     *
     * @return the invoice currency as a string, or {@code null} if not set.
     */
    public String getCurrency() {
        return amount != null ? amount.currency() : null;
    }
}