package at.spengergasse.photoalbum.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
// @AllArgsConstructor //asagida constructor create ettigimiz icin gerek kalmadi.
// @Builder  //cikardik cünkü cannot be instantiated hatasi veriyor

@MappedSuperclass
public abstract class AbstractPerson extends AbstractPersistable<Long> {

    @NotNull
    @Version
    private Integer version;

    @NotBlank
    @Email
    @Column(length = 255)
    private String userName;

    @NotNull
    @Column(length = 32)
    private String firstName;

    @NotNull
    @Column(length = 64)
    private String lastName;

    public AbstractPerson(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
