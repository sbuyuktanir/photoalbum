package at.spengergasse.photoalbum.domain;

import lombok.*;
import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //bu varsa ilave constructor yapmiyorsun.
@AllArgsConstructor
@Builder

@Entity
@Table(name="albums")

public class Album extends AbstractPersistable<Long> implements KeyHolder{

    public static final int KEY_LENGTH = 4;

    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @NotNull
    @Version
    private Integer version;

    @NotNull
    @Size(min=3, max=64)
    @Pattern(regexp = "[A-Z][A-Za-z0-9 -]{1,63}")
    @Column(name="album_name", length=64)
    private String name;

    @NotNull
    @PastOrPresent
    @Column(name = "creation_ts")
    private LocalDateTime creationTS;

    private Boolean restricted;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "album_photos",
                    joinColumns = @JoinColumn(name = "album_id", foreignKey = @ForeignKey(name= "FK_album_photos_2_album")))
    @OrderColumn(name= "position")
    private List<AlbumPhoto> photos = new ArrayList<>();  //List yerine ArrayList`de olabilir

//    @Builder.Default //notwendig, damit wir keine NPEx bekommen, wenn wir Builder partiell verwenden
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "album")
//    private List<AlbumPhotoContainment> containedPhotos = new ArrayList<>();

    /*
    Implemented as fluent API
    album.addPhoto(p1).addPhoto(p2).addPhoto(p3)
    @param photo
    @return
     */

    public Album addPhotos(LocalDateTime assignmentTS, Photo... photos){
        Arrays.stream(photos).forEach(photo -> addPhoto(assignmentTS, photo));
        return this;
    }

        public Album addPhoto(LocalDateTime assignmentTS, Photo photo){

        //if (photo == null) throw new NullPointerException("Photo must not be null!");
        //IF clause kullanmasan da olur. Farkli yöntemler var, use expression

            Objects.requireNonNull(assignmentTS, "Assignment timestamp must not be null!");
            Objects.requireNonNull(photo, "Photo must not be null!");

            AlbumPhoto albumPhoto = createAlbumPhoto(assignmentTS, photo);
            //ortak data icin asagida Method AlbumPhoto olusturdu. Burda ona referenz ediyor

            //photos.add(createAlbumPhoto(assignmentTS, photo)); //böyle de olur. O böyle yapmis.
            photos.add(albumPhoto);
                return this;

    }

    private AlbumPhoto createAlbumPhoto(LocalDateTime assignmentTS, Photo photo) {
        var albumPhoto = AlbumPhoto.builder()
                .photo(photo)
                .assignmentTS(assignmentTS)
                .build();

        return albumPhoto;

    //    return AlbumPhoto.builder().photo(photo).assignmentTS(assignmentTS).build(); //böyle de olur.

    }

    public Album insertPhoto(LocalDateTime assignmentTS, Photo photo, Integer position) {
        Objects.requireNonNull(assignmentTS, "Assignment timestamp must not be null!");
        Objects.requireNonNull(photo, "Photo must not be null!");
        Objects.requireNonNull(position, "Position must not be null!");

        AlbumPhoto albumPhoto = createAlbumPhoto(assignmentTS, photo);

/*
        var albumPhoto = AlbumPhoto.builder()
                .photo(photo)
                .assignmentTS(assignmentTS)
                .build();
*/

        //photos.add(position, createAlbumPhoto(assignmentTS, photo)); //böyle de olur
        photos.add(position, albumPhoto);
        return this;
    }

    public List<Photo> getPhotos(){
        // List<Photo> getPhotos(){
        return photos.stream()
                .map(AlbumPhoto::getPhoto)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<AlbumPhoto> getAlbumPhotos() {
//        return Collections.unmodifiableList(photos);
        return photos;
    }

    public Integer getPhotoCount() {

        return photos.size();
    }

    public Long getPhotoCountFor(Predicate<Photo> condition) {

        return photos.stream()
                .filter(p -> condition.test(p.getPhoto()))
                .count();
    }


//    public Album() {  }  //constructor olursa @NoArgsConstructor gerek yok.

    /*
    List<Photo> getContainedPhotos(){
        return containedPhotos.stream()
                .map(AlbumPhotoContainment::getPhoto)
                .collect(Collectors.toUnmodifiableList());
    }

    List<AlbumPhotoContainment> getAlbumPhotoContainment(){
        return Collections.unmodifiableList(containedPhotos);
    }
     */

}
