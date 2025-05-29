package com.qu3dena.aquaengine.backend.profiles.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.profiles.domain.model.commands.CreateProfileCommand;
import com.qu3dena.aquaengine.backend.profiles.domain.model.valuobjects.*;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Aggregate root representing a user profile.
 * <p>
 * Encapsulates personal, contact, and company information for a user profile.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "profiles")
@EqualsAndHashCode(callSuper = true)
public class ProfileAggregate extends AuditableAbstractAggregateRoot<ProfileAggregate> {

    /**
     * Unique identifier for the user.
     */
    @Column(unique = true, nullable = false)
    private Long userId;

    /**
     * Embedded value object containing the user's first and last name.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private PersonName personName;

    /**
     * Embedded value object containing the user's contact email address.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "contact_email"))})
    private ContactEmail contactEmail;

    /**
     * Embedded value object containing the user's contact phone number.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "contact_phone"))
    })
    private ContactPhone contactPhone;

    /**
     * Embedded value object containing the user's company information.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "company_name")),
            @AttributeOverride(name = "street", column = @Column(name = "company_street")),
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "postalCode.value", column = @Column(name = "company_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "company_country"))
    })
    private CompanyInfo companyInfo;

    /**
     * Constructs a {@code ProfileAggregate} with the provided details.
     *
     * @param firstName    User's first name.
     * @param lastName     User's last name.
     * @param contactEmail User's contact email address.
     * @param contactPhone User's contact phone number.
     * @param companyName  Company name.
     * @param street       Company street address.
     * @param city         Company city.
     * @param postalCode   Company postal code.
     * @param number       Company phone number.
     * @param country      Company country.
     */
    public ProfileAggregate(String firstName, String lastName, String contactEmail, String contactPhone, String companyName,
                            String street, String city, String postalCode, String number, String country) {
        this.personName = new PersonName(firstName, lastName);
        this.contactEmail = new ContactEmail(contactEmail);
        this.contactPhone = new ContactPhone(contactPhone);
        this.companyInfo = new CompanyInfo(companyName, street, city,
                new PostalCode(postalCode), country);
    }

    /**
     * Constructs a {@code ProfileAggregate} from a {@link CreateProfileCommand}.
     *
     * @param command The command containing all profile creation data.
     */
    public ProfileAggregate(CreateProfileCommand command) {
        this.userId = command.userId();
        this.personName = new PersonName(command.firstName(), command.lastName());
        this.contactEmail = new ContactEmail(command.contactEmail());
        this.contactPhone = new ContactPhone(command.contactPhone());
        this.companyInfo = new CompanyInfo(
                command.companyName(),
                command.companyStreet(),
                command.companyCity(),
                new PostalCode(command.postalCode()),
                command.companyCountry()
        );
    }

    /**
     * Factory method to create a {@code ProfileAggregate} from a {@link CreateProfileCommand}.
     *
     * @param command The command containing all profile creation data.
     * @return A new {@code ProfileAggregate} instance.
     */
    public static ProfileAggregate create(CreateProfileCommand command) {
        return new ProfileAggregate(command);
    }

    /**
     * Returns the user's full name.
     *
     * @return Full name as a string.
     */
    public String getFullName() {
        return personName.getFullName();
    }

    /**
     * Returns the user's contact email address.
     *
     * @return Contact email as a string.
     */
    public String getContactEmail() {
        return contactEmail.value();
    }

    /**
     * Returns the user's contact phone number.
     *
     * @return Contact phone number as a string.
     */
    public String getContactPhone() {
        return contactPhone.number();
    }

    /**
     * Returns the user's company information as a formatted string.
     *
     * @return Company information as a string.
     */
    public String getCompanyInfo() {
        return companyInfo.getCompanyInfo();
    }
}