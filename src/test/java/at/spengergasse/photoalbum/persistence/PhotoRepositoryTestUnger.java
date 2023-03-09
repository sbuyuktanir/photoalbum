package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.TestFixtures;
import at.spengergasse.photoalbum.domain.Country;
import at.spengergasse.photoalbum.domain.Person;
import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static at.spengergasse.photoalbum.TestFixtures.createPhoto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@Slf4j
@DataJpaTest  //remove this, when you use class with AbstractTest
@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

class PhotoRepositoryTestUnger {

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

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

        Country austria = TestFixtures.austria;
        Photographer uk = TestFixtures.uk;  //TestFixtures control et...
//        Country at = austria();
//        Photographer uk = uk(at);

        Photo photo = createPhoto("DSC_4711.png", "Photo 1", uk);

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

        Country austria = TestFixtures.austria;
        Photographer uk = TestFixtures.uk;  //TestFixtures control et...
//        Country at = austria();
//        Photographer uk = uk(at);

//        Photographer uk = photographerRepository.findByUserName("unger@spengergasse.at").orElse(TestFixtures.uk);
        Photo photo = createPhoto("DSC_4711.png", "Photo 1", uk)
                .tagPerson(jg, 5);  //createPhoto with tagged Person

        //when /act
      var saved = photoRepository.saveAndFlush(photo);  //Photo yazma, kendi geliyor.
//        var saved = photoRepository.save(photo);  //Photo yazma, kendi geliyor.

        //then /assert
        assertThat(saved).isSameAs(photo);
        assertThat(saved.getId()).isNotNull();

    }

    @Disabled  //ignore test
    @Test
    void ensureReadingPhotoInfoDTOWorksCorrectly() {

        //given /arrange
        Person jg = Person.builder().userName("grueneis@spg.at").firstName("Joachim").lastName("Grüneis").nickName("JG").build();

        Country austria = TestFixtures.austria;
        Photographer uk = TestFixtures.uk;  //TestFixtures control et...
//        Country at = austria();
//        Photographer uk = uk(at);

        Photo photo = createPhoto("DSC_4711.png", "Photo 1", uk)
                .tagPerson(jg, 5);  //createPhoto with tagged Person

        photoRepository.saveAndFlush(photo);

        //when /act
        var photoInfo = photoRepository.findAllByNameLike("Photo%");
//        var saved = photoRepository.save(photo);  //Photo yazma, kendi geliyor.

        //then /assert
        assertThat(photoInfo).isNotNull().isNotEmpty();
        assertThat(photoInfo.get(0).name()).isEqualTo("Photo 1");

    }

}