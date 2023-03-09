package at.spengergasse.photoalbum.service;


import at.spengergasse.photoalbum.domain.Country;
import at.spengergasse.photoalbum.persistence.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)

public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
