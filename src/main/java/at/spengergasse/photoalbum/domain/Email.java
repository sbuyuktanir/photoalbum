package at.spengergasse.photoalbum.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Embeddable
public class Email {

    @Column(name = "email_address", length = 128)
    private  String  address;

    @Column(name = "email_type", columnDefinition = "CHAR(1) CHECK (email_type IN ('B', 'P'))")
    private EmailType type;

}
