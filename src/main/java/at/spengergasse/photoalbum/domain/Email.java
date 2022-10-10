package at.spengergasse.photoalbum.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class Email {

    private  String  address;
    private EmailType type;

}
