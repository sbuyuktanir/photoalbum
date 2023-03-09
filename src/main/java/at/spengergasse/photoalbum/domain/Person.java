package at.spengergasse.photoalbum.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder

@Entity
@Table(name="persons")
public class Person extends AbstractPerson{

    @NotNull
    @Column(length=32)
    private String nickName;

    @Builder
    public Person(String userName, String firstName, String lastName, String nickName) {
        super(userName, firstName, lastName);
        this.nickName = nickName;

    }

}
