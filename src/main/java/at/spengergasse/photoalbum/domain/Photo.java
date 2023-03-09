package at.spengergasse.photoalbum.domain;

import at.spengergasse.photoalbum.persistence.converter.OrientationConverter;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@ToString(of = {"id", "version", "name", "creationTS"})
@ToString(callSuper = true, of = {"version", "name", "creationTS"})


@Entity
@Table(name="photos")  //Database Table creation

public class Photo extends AbstractPersistable<Long> implements KeyHolder {  //Klasse Richtung Datenbank, Long ist primärschlüssel ID

    public static final int KEY_LENGTH = 5;

    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @NotNull
    @Version
    private Integer version;

    @NotBlank
    @Column(length = 64, unique = true)
    private String fileName;

    @NotNull
    @Column(name = "photo_name", length = 64)
    private String name;

    @NotNull
    @PastOrPresent
    @Column(name = "creation_ts")  //column name change
    private LocalDateTime creationTS;

    @Min(0) @Max(32767)
    private Integer width;
    @Min(0) @Max(32767)
    private Integer height;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_photo_photographer"))
    private Photographer photographer;  //Photographer.java`da @Entity ekle
//  @Enumerated(EnumType.STRING)  //instead of this, we use @Converter(autoApply = true)
//  @Convert(converter = OrientationConverter.class)

    @Column(length = 1)  //varchar(255) idi, memory`de yer kaplamasin diye 1 yaptik.
    private Orientation orientation;

    @Builder.Default
//    @Transient // @Transient annotation is used to indicate that a field is not to be persisted in the database
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "photo")
    private List<TaggedPerson> taggedPersons = new ArrayList<>(2);

    public Photo tagPerson(Person person, Integer rating) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(rating);

        taggedPersons.add(TaggedPerson.builder().photo(this).person(person).rating(rating).build());
        return this;

    }
}
