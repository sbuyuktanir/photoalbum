package at.spengergasse.photoalbum.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// @Builder

@Entity
@Table(name="photographers")  //Database Table creation

public class Photographer extends AbstractPerson implements KeyHolder{
    //primary key atamak icin AbstractPerson extends AbstractPersistable<Long> yaptik.
    //AbstractPerson`a @MappedSuperclass ekledik.

    public static final int KEY_LENGTH = 4;
    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @Embedded  //Entity Photographer, die vier Attribute von Address.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="streetNumber", column = @Column(name = "studio_street_number", length=64)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "studio_zip_code", length=16)),
            @AttributeOverride(name = "city", column = @Column(name = "studio_city", length=64)),
    })
    @AssociationOverrides({   //Country Tabelle ile Beziehung. Country ist hier Fremdeschlüssel
            @AssociationOverride(name = "country",
                                joinColumns = {@JoinColumn(name = "studio_country_id")},
                                foreignKey = @ForeignKey(name = "FK_photographers_studio_country"))
    })
    private at.spengergasse.photoalbum.domain.Address studioAddress;

    @Embedded  //Entity Photographer, die vier Attribute von Address.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="streetNumber", column = @Column(name = "billing_street_number", length=64)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code", length=16)),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city", length=64)),
    })
    @AssociationOverrides({   //Country Tabelle ile Beziehung. Country ist hier Fremdeschlüssel
            @AssociationOverride(name = "country",
                    joinColumns = {@JoinColumn(name = "billing_country_id")},
                    foreignKey = @ForeignKey(name = "FK_photographers_billing_country"))
    })
    private Address billingAddress;

    @Embedded  //Entity Photographer, die vier Attribute von Address.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="countryCode", column = @Column(name = "mobile_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "mobile_area_code")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "mobile_serial_number", length=16)),
    })
    private PhoneNumber mobilePhoneNumber;

    @Embedded  //Entity Photographer, die vier Attribute von Address.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="countryCode", column = @Column(name = "business_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "business_area_code")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "business_serial_number", length=16)),
    })
    private PhoneNumber businessPhoneNumber;

//    @Builder.Default
    @ElementCollection  //Photographer`in birden fazla email oldugu icin CollectionTable create ediyoruz.
    @CollectionTable(name="photographer_emails", joinColumns=@JoinColumn(name="photographer_id",
    foreignKey = @ForeignKey(name="FK_photographer_emails")))
    @OrderColumn(name="position")
    private Set<EmailAddress> emailAddresses = new HashSet<>(2);  //cift email oldugu icin

    @Builder
    public Photographer(String key, String userName, String firstName, String lastName, Address studioAddress,
                        Address billingAddress, PhoneNumber mobilePhoneNumber, PhoneNumber businessPhoneNumber) {
        super(userName, firstName, lastName);
        this.key = key;
        this.studioAddress = studioAddress;
        this.billingAddress = billingAddress;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.businessPhoneNumber = businessPhoneNumber;
        this.emailAddresses = emailAddresses == null ? new HashSet<>(2) : new HashSet<>(emailAddresses);
//        this.emailAddresses = new HashSet<>(emailAddresses);
//        this.emails = Optional.ofNullable(emails).orElseGet(HashSet::new);
    }

    public Set<EmailAddress> getEmailAddresses() {
        return Collections.unmodifiableSet(emailAddresses);
    }

//    @Builder
//    public Photographer(String userName, String firstName, String lastName, Address studioAddress,
//                        Address billingAddress, PhoneNumber mobilePhoneNumber, PhoneNumber businessPhoneNumber, Set<Email> emails) {
//        this(userName, firstName, lastName, studioAddress, billingAddress, mobilePhoneNumber, businessPhoneNumber);
//        Set<at.spengergasse.photoalbum.domain.Email> email = null;
//        this.emails = getEmails();
//        this.emails = emails;
//    }

    public Photographer addEmails(EmailAddress... emailAddresses){
        Arrays.stream(emailAddresses).forEach(this.emailAddresses::add);
        return this;
    }

    public String getDisplayName() {
        return "%s %s".formatted(getFirstName(), getLastName().toUpperCase());
    }
}
