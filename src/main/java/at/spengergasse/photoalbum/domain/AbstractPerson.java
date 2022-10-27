package at.spengergasse.photoalbum.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder  //cikardik cünkü cannot be instantiated hatasi veriyor

@MappedSuperclass
public abstract class AbstractPerson extends AbstractPersistable<Long> {
    @Column(length = 32)
    private String userName;
    @Column(length = 32)
    private String firstName;
    @Column(length = 64)
    private String lastName;

}
