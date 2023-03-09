package at.spengergasse.photoalbum.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

//@Entity   //value object oldugunda bunlara gerek yok.
//@Table(name="album_photo_containments")
//public class AlbumPhotoContainment extends AbstractPersistable<Long> {  //Long is PK ID

@Embeddable
public class AlbumPhoto {

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(foreignKey = @ForeignKey(name = "FK_album_photo_containments_2_albums"))
//    private Album album;  //artik aradaki iliski Entity olmadigi icin burada declare etmeye gerek yok.

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_album_photos_2_photos"))
    private Photo photo;

//    @Min(1) @Max(999)
//    private Integer position;  // Position zaten Album.java`da girili.

    @PastOrPresent
    @Column(name = "assignment_ts")
    private LocalDateTime assignmentTS;

}

