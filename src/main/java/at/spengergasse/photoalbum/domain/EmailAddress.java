package at.spengergasse.photoalbum.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Embeddable  //value object
public class EmailAddress {

    @Column(name = "email_address", length = 128)
    @javax.validation.constraints.Email  //make the Email Anotation
    private  String  address;

    @Column(name = "email_type", columnDefinition = "CHAR(1) CHECK (email_type IN ('B', 'P'))")
    private EmailType type;

}
