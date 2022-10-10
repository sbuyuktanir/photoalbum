package at.spengergasse.photoalbum.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder  //cikardik cünkü cannot be instantiated hatasi veriyor

public abstract class AbstractPerson {
    private String userName;
    private String firstName;
    private String lastName;

}
