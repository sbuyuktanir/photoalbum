package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.TestFixtures;
import at.spengergasse.photoalbum.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Set;

import static at.spengergasse.photoalbum.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotographerRepository photographerRepository;

    @BeforeEach
    void setup() {
        assumeThat(photoRepository).isNotNull();
    }

    @Disabled
    @Test
    void ensureSaveAndRereadOfPhotoWorksCorrectly() {

        //given /arrange
//        Country at = austria();
//        Photographer uk = uk(at);
        Photo photo = createPhoto("DSC_4711.png", "Photo 1", TestFixtures.uk);

        //when /act
//      var saved = photoRepository.saveAndFlush(photo);  //Photo yazma, kendi geliyor.
        var saved = photoRepository.save(photo);  //Photo yazma, kendi geliyor.

        //then /assert
        assertThat(saved).isSameAs(photo);
        assertThat(saved.getId()).isNotNull();

    }


    @Disabled  //ignore test
    @Test
    void ensureSavePhotoWithTaggedPersonWorksCorrectly() {

        //given /arrange
        Person jg = Person.builder().userName("grueneis@spg.at").firstName("Joachim").lastName("Grüneis").nickName("JG").build();
//        Country at = austria();
//        Photographer uk = uk(at);
        Photographer uk = photographerRepository.findByUserName("unger@spengergasse.at").orElse(TestFixtures.uk);
        Photo photo = createPhoto("DSC_4711.png", "Photo 1", uk)
                .tagPerson(jg, 5);

        //when /act
      var saved = photoRepository.saveAndFlush(photo);  //Photo yazma, kendi geliyor.
//        var saved = photoRepository.save(photo);  //Photo yazma, kendi geliyor.

        //then /assert
        assertThat(saved).isSameAs(photo);
        assertThat(saved.getId()).isNotNull();

    }
}