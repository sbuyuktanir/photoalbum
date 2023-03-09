package at.spengergasse.photoalbum;

import at.spengergasse.photoalbum.domain.*;

import java.time.LocalDateTime;

public class TestFixtures {

/*
    public static Country turkei(){
        return Country.builder().name("Türkei").iso2Code("TR").build();
    }

    public static Country schweiz(){
        return Country.builder().name("Schweiz").iso2Code("CH").build();
    }
*/

    public static Country austria = Country.builder().name("Österreich").iso2Code("AT").build();
    public static Country germany = Country.builder().name("Deutschland").iso2Code("DE").build();

//    public static Address spengergasse20(Country country) {

/*
    public static Address spengergasse20() {
        return Address.builder()
                .streetNumber("Spengergasse 20")
                .zipCode("1050")
                .city("Wien")
                .country(austria)
                .build();
    }

*/

    public static Address spengergasse20 = spengergasse(20);  //asagida ortak static Address Methode var
    public static Address spengergasse21 = spengergasse(21);

    public static PhoneNumber ukMobilePhoneNumber = PhoneNumber.builder().countryCode(43).areaCode(664).serialNumber("12341234").build();

//    public static Email ukBusinessEmail = Email.builder().address("unger@spg.at").type(EmailType.BUSINESS).build();

    public static EmailAddress ukBusinessEmailAddress = createSpgEmail("unger");  //asagida ortak static Email Methode var
    public static EmailAddress weaverBusinessEmailAddress = createSpgEmail("weaver");

    public static Photographer uk = Photographer.builder()
            .key("uk69")
            .userName("unger@spengergasse.at")
            .firstName("Klaus")
            .lastName("Unger")
            .studioAddress(spengergasse20)
            .billingAddress(spengergasse21)
            .mobilePhoneNumber(ukMobilePhoneNumber)
//            .emails(Set.of(ukBusinessEmail, weaverBusinessEmail))
//            .build();
            .build().addEmails(ukBusinessEmailAddress, weaverBusinessEmailAddress);


    public static Photo createPhoto(String fileName, String name) {
        return createPhoto(fileName, name, uk);
    }
    public static Photo createPhoto(String fileName, String name, Photographer photographer){
        return Photo.builder()
            .fileName(fileName)
            .name(name)
//          .fileName("DSC-890.jpg")
//          .name("My first Bild")
            .width(640)
            .height(480)
            .creationTS(LocalDateTime.now())
            .orientation(Orientation.LANDSCAPE)
//            .photographer(Photographer.builder().build())  //validation hatasi veriyor. Bos zannediyor.
            .photographer(uk)
            .build();

}


    private static EmailAddress createSpgEmail(String username) {
        return EmailAddress.builder().address("%s@spg.at".formatted(username)).type(EmailType.BUSINESS).build() ;
    }

    public static Address spengergasse(Integer number) {
        return Address.builder()
                .streetNumber("Spengergasse %d".formatted(number))
                .zipCode("1050")
                .city("Wien")
                .country(austria)
                .build();
    }

}
