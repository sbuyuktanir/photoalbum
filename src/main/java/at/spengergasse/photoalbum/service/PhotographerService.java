package at.spengergasse.photoalbum.service;


import at.spengergasse.photoalbum.domain.Address;
import at.spengergasse.photoalbum.domain.Orientation;
import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.foundation.Base58;
import at.spengergasse.photoalbum.foundation.DateTimeFactory;
import at.spengergasse.photoalbum.persistence.CountryRepository;
import at.spengergasse.photoalbum.persistence.PhotoRepository;
import at.spengergasse.photoalbum.persistence.PhotographerRepository;
import at.spengergasse.photoalbum.service.commands.CreatePhotographerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)

public class PhotographerService {

    private final PhotographerRepository photographerRepository;
    private final CountryRepository countryRepository;
    private final Base58 base58;

//    private final SimpleJpaRepository simpleJpaRepository;
//    private final DateTimeFactory dateTimeFactory;

    @Transactional
    public Photographer createPhotographer(CreatePhotographerCommand cmd){
        return createPhotographer(
                cmd.userName(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.billingAddressStreetNumber(),
                cmd.billingAddressZipCode(),
                cmd.billingAddressCity(),
                cmd.billingAddressCountryIso2Code());
    }
    @Transactional
    public Photographer createPhotographer(String userName, String firstName, String lastName,
                                           String billingAddressStreetNumber, String billingAddressZipCode,
                                           String billingAddressCity, String billingAddressCountryIso2Code) {

        return countryRepository.findByIso2Code(billingAddressCountryIso2Code)
                .map(country -> Photographer.builder()
                        .key(base58.randomString(Photographer.KEY_LENGTH))
                        .userName(userName)
                        .firstName(firstName)
                        .lastName(lastName)
                        .billingAddress(Address.builder()
                                .streetNumber(billingAddressStreetNumber)
                                .zipCode(billingAddressZipCode)
                                .city(billingAddressCity)
                                .country(country)
                                .build())
                        .build())
                .map(photographerRepository::save)
                .orElseThrow();
    }

    @Transactional(readOnly = false)
    public Optional<Photographer> updatePhotographerUserName(String key, String newUserName) {
        return photographerRepository.findByKey(key)
                .map(p -> {
                    p.setUserName(newUserName);
//                    return photoRepository.save(p);  //save ist nicht Muss, nicht Notwendig
                    return p;
                });
    }

    @Transactional(readOnly = false)
    public void updatePhotographer(String Key, String userName, String firstName, String lastName) {
        photographerRepository.findByKey(Key).ifPresent(p -> {
            p.setUserName(userName);
            p.setFirstName(firstName);
            p.setLastName(lastName);
        });
    }

    @Transactional(readOnly = false)
    public void deleteByKey(String key) {
        photographerRepository.deleteByKey(key);
    }

    public List<Photographer> getAllPhotographers() {

        return photographerRepository.findAll();
    }

    public Optional<Photographer> getPhotographer(Long id) {

        return photographerRepository.findById(id);
    }

    public Optional<Photographer> getPhotographer(String key) {

        return photographerRepository.findByKey(key);
    }

    public Optional<Photographer> findByKey(String key) {

        return photographerRepository.findByKey(key);
    }

    public void deleteKey(String key) {
        photographerRepository.deleteByKey(key);
    }

    @Transactional(readOnly = false)
    public void updatePhotographer(String Key , String userName, String firstName, String lastName, String billingAddressCity,
                                   String billingAddressStreetNumber, String billingAddressZipCode, String billingAddressCountryIso2Code) {
        photographerRepository.findByKey(Key).ifPresent(p -> {
            p.setUserName(userName);
            p.setFirstName(firstName);
            p.setLastName(lastName);
        });
    }
}
