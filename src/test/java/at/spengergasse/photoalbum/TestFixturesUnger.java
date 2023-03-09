package at.spengergasse.photoalbum;

import at.spengergasse.photoalbum.domain.*;

import java.time.LocalDateTime;
import java.util.Set;

public class TestFixturesUnger {

    public static Country austria() {
        return Country.builder().name("Österreich").iso2Code("AT").build();
    }

    public static Country germany() {
        return Country.builder().name("Deutschland").iso2Code("DE").build();
    }

    public static PhoneNumber ukMobilePhoneNumber = PhoneNumber.builder().countryCode(43).areaCode(664).serialNumber("12341234").build();

    public static EmailAddress ukBusinessEmailAddress = createSpgEmail("unger");  //asagida ortak static Email Methode var
    public static EmailAddress weaverBusinessEmailAddress = createSpgEmail("weaver");

    public static Photographer uk (Country country) {
        return Photographer.builder()
                .key("uk69")
                .userName("unger@spengergasse.at")
                .firstName("Klaus")
                .lastName("Unger")
                .studioAddress(spengergasse(20, country))
                .billingAddress(spengergasse(21, country))
                .mobilePhoneNumber(ukMobilePhoneNumber)
//                .emailAddresses(Set.of(EmailAddress.builder().address("uk@spg.at").type(EmailType.BUSINESS).build()))
                .build();
//                .build().addEmails(ukBusinessEmailAddress, weaverBusinessEmailAddress);
    }

    public static Photo createPhoto(String fileName, String name, Photographer photographer){
        return Photo.builder()
                .fileName(fileName)
                .name(name)
                .width(640)
                .height(480)
                .creationTS(LocalDateTime.now())
                .orientation(Orientation.LANDSCAPE)
//            .photographer(Photographer.builder().build())  //validation hatasi veriyor.
                .photographer(photographer)
                .build();
    }

    private static EmailAddress createSpgEmail(String username) {
        return EmailAddress.builder().address("%s@spg.at".formatted(username)).type(EmailType.BUSINESS).build() ;
    }

    public static Address spengergasse(Integer number, Country country) {
        return Address.builder()
                .streetNumber("Spengergasse %d".formatted(number))
                .zipCode("1050")
                .city("Wien")
                .country(country)
                .build();
    }

}
