package at.spengergasse.photoalbum.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@Table(name="photos")  //Database Table creation

public class Photo extends AbstractPersistable<Long> {  //Klasse Richtung Datenbank, Long ist primärschlüssel ID
    @Column(length = 64)
    private String fileName;
    @Column(length = 64)
    private String name;
    @Column(name = "creation_ts")  //column name change
    private LocalDateTime creationTS;
    private Integer width;
    private Integer height;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_photo_photographer"))
    private Photographer photographer;  //Photographer.java`da @Entity ekle
//  @Enumerated(EnumType.STRING)  // we use @Converter(autoApply = true)
    @Column(length = 1)  //varchar(255) idi, memory`de yer kaplamasin diye 1 yaptik.
    private Orientation orientation;
}
