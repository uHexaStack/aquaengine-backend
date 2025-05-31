
package com.qu3dena.aquaengine.backend.shared.domain.model.valuobjects;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value object representing a monetary amount with currency.
 */
@Embeddable
public record Money(BigDecimal amount, String currency) {

    /**
     * Constructs a {@code Money} instance and validates the amount and currency.
     *
     * @param amount   the monetary amount, must be non-negative
     * @param currency the currency code, must be non-null and non-blank
     * @throws IllegalArgumentException if amount is negative or currency is invalid
     */
    public Money {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Amount cannot be negative");

        if (currency == null || currency.isBlank())
            throw new IllegalArgumentException("Currency cannot be null or blank");
    }

    /**
     * Adds another {@code Money} object to this one.
     *
     * @param other the other money object
     * @return a new {@code Money} instance with the summed amount
     * @throws IllegalArgumentException if currencies do not match
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Cannot add money with different currencies");

        return new Money(this.amount.add(other.amount), this.currency);
    }

    /**
     * Subtracts another {@code Money} object from this one.
     *
     * @param other the other money object
     * @return a new {@code Money} instance with the subtracted amount
     * @throws IllegalArgumentException if currencies do not match
     */
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Cannot subtract money with different currencies");

        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /**
     * Multiplies the amount by a given factor.
     *
     * @param factor the multiplication factor
     * @return a new {@code Money} instance with the multiplied amount
     */
    public Money multiply(int factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }

    @Override
    public String toString() {
        return String.format("%s %s", amount.toPlainString(), currency);
    }
}