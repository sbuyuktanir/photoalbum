package at.spengergasse.photoalbum.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public abstract class AbstractPerson {
    private String userName;
    private String firstName;
    private String lastName;

}
