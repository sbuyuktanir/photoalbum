package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Set;

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

        //given /arrange
        Country aut = Country.builder().name("Österreich").iso2Code("AT").build();
        
        Address spg20 = Address.builder()
                .streetNumber("Spengergasse 20")
                .zipCode("1050")
                .city("Wien")
                .country(aut)
                .build();

        Address spg21 = Address.builder()
                .streetNumber("Spengergasse 21")
                .zipCode("1050")
                .city("Wien")
                .country(aut)
                .build();
        
        PhoneNumber mobilePhoneNumber = PhoneNumber.builder()
                .countryCode(43)
                .areaCode(660)
                .serialNumber("1234567")
                .build();

        Email uk_spg_at = Email.builder().address("unger@spg.at").type(EmailType.BUSINESS).build();
        
        Photographer uk = Photographer.builder()
                .userName("uk")
                .firstName("Klaus")
                .lastName("Unger")
                .studioAddress(spg20)
                .billingAddress(spg21)
                .mobilePhoneNumber(mobilePhoneNumber)
                .emails(Set.of(uk_spg_at))
                .build();
        
        Photo photo = Photo.builder()
                .fileName("DSC-890.jpg")
                .name("My first Bild")
                .width(640)
                .height(480)
                .creationTS(LocalDateTime.now())
                .orientation(Orientation.LANDSCAPE)
                .photographer(uk)
                .build();
/*
        Photographer sbt = Photographer.builder()
                .userName("sbt")
                .firstName("Selcuk")
                .lastName("Büyüktanir")
                .studioAddress(spg20)
                .build();
*/
        Photo photo2 = Photo.builder()
                .fileName("DSC-42.jpg")
                .name("My second Bild")
                .width(420)
                .height(240)
                .creationTS(LocalDateTime.now())
                .orientation(Orientation.SQUARE)
                .photographer(uk)
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