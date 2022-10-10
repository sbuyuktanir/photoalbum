package at.spengergasse.photoalbum.domain;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class Photographer extends AbstractPerson {

    private at.spengergasse.photoalbum.domain.Address studioAddress;
    private Address billingAddress;
    private PhoneNumber mobilePhoneNumber;
    private PhoneNumber businessPhoneNumber;
    private Set<Email> emails;

}
