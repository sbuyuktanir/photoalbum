package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.TestFixtures;
import at.spengergasse.photoalbum.domain.Album;
import at.spengergasse.photoalbum.domain.Country;
import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static at.spengergasse.photoalbum.TestFixtures.createPhoto;
import static at.spengergasse.photoalbum.TestFixtures.uk;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@Slf4j
@DataJpaTest  //remove this and remove @import, when you use class with AbstractTest
@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

class AlbumRepositoryTestUnger {

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PhotographerRepository photographerRepository;

    private Album album;

    private Photo p1;
    private Photo p2;
    private Photo p3;
    private Photo p4;

    @BeforeEach
    void setup() {
        assumeThat(albumRepository).isNotNull();

        Country austria = TestFixtures.austria;
        Photographer uk = TestFixtures.uk;  //TestFixtures control et...
//        Country austria = austria();
//        Photographer uk = uk(austria);

        p1 = createPhoto("DSC_4711.png", "Photo 1", uk);
        p2 = createPhoto("DSC_4258.png", "Photo 2", uk);
        p3 = createPhoto("DSC_1972.png", "Photo 3", uk);

        album = Album.builder()
                .name("My existing Album")
                .restricted(false)
                .creationTS(LocalDateTime.now())
                .build()
                .addPhotos(LocalDateTime.now(), p1, p2, p3);

//        albumRepository.save(album);
        var saved = albumRepository.saveAndFlush(album);
//        saved = albumRepository.save(album);

        //then assert
        assertThat(saved).isSameAs(album);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();
        List.of(p1, p2, p3).forEach(photo -> {  //tekrari önlemek icin forEach Loop kullandi.
            assertThat(photo.getId()).isNotNull();
            assertThat(photo.getVersion()).isNotNull();
        });
        assertThat(uk.getId()).isNotNull();
        assertThat(uk.getVersion()).isNotNull();
        assertThat(austria.getId()).isNotNull();
    }

    @Disabled
    @Test
    void ensureSaveOfAlbumWithInsertedPhotosWorksCorrectly() {

        //given /arrange
//        Photo p1 = createPhoto("DSC_4711.png", "Photo 1");
//        Photo p2 = createPhoto("DSC_4258.png", "Photo 2");
//        Photo p3 = createPhoto("DSC_1972.png", "Photo 3");
//        Photo p4 = createPhoto("DSC_890.png", "Photo 4");

        // önceden ne vardi görmek icin
        // D:\SELCUK\HTLselcuk\POS_Pratik\POS_Semester78\JavaCodes\AlbumRepositoryTest.java

/*
        Album album = Album.builder()
                .name("My existing Album")
                .restricted(false)
                .creationTS(LocalDateTime.now())
                .build()
                .addPhotos(LocalDateTime.now(), p1, p2, p3);

        albumRepository.saveAndFlush(album);
        */

        Photographer uk = photographerRepository.findByUserName("unger@spengergasse.at").orElseThrow();

        //when act
        p4 = createPhoto("DSC_890.png", "Photo 4", uk);

        album.insertPhoto(LocalDateTime.now(), p4, 1);
        albumRepository.saveAndFlush(album);  //:Album yazma, kendi geliyor.

//        var saved = albumRepository.saveAndFlush(album);  //:Album yazma, kendi geliyor.

    }


    @Disabled
    @Test
    void ensureRetrievalOfAlbumInfoDTOWorksCorrectly() {

        //given
        Photographer uk = photographerRepository.findByUserName("unger@spengergasse.at").orElseThrow();

        p4 = createPhoto("DSC_890.png", "Photo 4", uk);

        album.insertPhoto(LocalDateTime.now(), p4, 1);
        albumRepository.saveAndFlush(album);

        //when
        var albumInfo = albumRepository.findAllByNameLike("%existing%");

        //then
        assertThat(albumInfo).isNotNull().isNotEmpty();
        assertThat(albumInfo.get(0).name()).isEqualTo("My existing Album");

    }


    @Disabled
    @Test
    void ensureSaveOfAlbumWithAddedPhotosWorksCorrectly() {

        //given
        Photographer uk = photographerRepository.findByUserName("unger@spengergasse.at").orElseThrow();

        p4 = createPhoto("DSC_890.png", "Photo 4", uk);

        //expect
        album.addPhoto(LocalDateTime.now(), p4);
        albumRepository.saveAndFlush(album);  //:Album yazma, kendi geliyor.


//        Photo p1 = createPhoto("DSC_4711.png", "Photo 1");
//        Photo p2 = createPhoto("DSC_4258.png", "Photo 2");
//        Photo p3 = createPhoto("DSC_1972.png", "Photo 3");
//        Photo p4 = createPhoto("DSC_890.png", "Photo 4");

        // önceden ne vardi görmek icin
        // D:\SELCUK\HTLselcuk\POS_Pratik\POS_Semester78\JavaCodes\AlbumRepositoryTest.java

        /*
        Album album = Album.builder()
                .name("My first Album")
                .restricted(false)
                .creationTS(LocalDateTime.now())
                .build()
                .addPhotos(LocalDateTime.now(), p1, p2, p3, p4);
*/

        //when act
//        var saved = albumRepository.saveAndFlush(album);  //:Album yazma, kendi geliyor.
//        var saved = albumRepository.save(album);  //:Album yazma, kendi geliyor.

/*
        //then assert
        assertThat(saved).isSameAs(album);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();

//        assertThat(p1.getId()).isNotNull();
//        assertThat(p1.getVersion()).isNotNull();
//        assertThat(p2.getId()).isNotNull();
//        assertThat(p2.getVersion()).isNotNull();

        List.of(p1, p2, p3).forEach(photo -> {  //tekrari önlemek icin forEach Loop kullandi.
            assertThat(photo.getId()).isNotNull();
            assertThat(photo.getVersion()).isNotNull();
                });

        assertThat(uk.getId()).isNotNull();
        assertThat(uk.getVersion()).isNotNull();
        assertThat(austria.getId()).isNotNull();
*/

    }
}