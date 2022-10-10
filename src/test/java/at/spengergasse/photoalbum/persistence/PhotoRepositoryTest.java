package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.Orientation;
import at.spengergasse.photoalbum.domain.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository photoRepository;

    @BeforeEach
    void setup() {
        assumeThat(photoRepository).isNotNull();
    }

    @Test
    void ensureSaveAndRereadOfPhotoWorksCorrectly() {

        //given
        Photo photo = Photo.builder()
                .fileName("DSC-890.jpg")
                .name("My first Bild")
                .width(640)
                .height(480)
                .creationTS(LocalDateTime.now())
                .orientation(Orientation.LANDSCAPE)
                .build();

        Photo photo2 = Photo.builder()
                .fileName("DSC-42.jpg")
                .name("My second Bild")
                .width(420)
                .height(240)
                .creationTS(LocalDateTime.now())
                .orientation(Orientation.SQUARE)
                .build();

        //when
        var saved = photoRepository.saveAndFlush(photo);  //Photo yazma, kendi geliyor.
        var saved2 = photoRepository.saveAndFlush(photo2);  //Photo yazma, kendi geliyor.

        //then
        assertThat(saved).isSameAs(photo);
        assertThat(saved.getId()).isNotNull();

        assertThat(saved2).isSameAs(photo2);
        assertThat(saved2.getId()).isNotNull();
    }

}