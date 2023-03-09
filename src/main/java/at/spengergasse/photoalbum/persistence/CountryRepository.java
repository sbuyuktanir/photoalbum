package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByIso2Code(String billingAddressCountryIso2Code);
}
