package at.spengergasse.photoalbum.persistence;


import at.spengergasse.photoalbum.domain.Album;
import at.spengergasse.photoalbum.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long>, KeyHolderQueries<Photographer> {

//    List<Photographer> findByUserName(String s);
    Optional<Photographer> findByUserName(String s);


    Optional<Photographer> findByKey(String key);

    Photographer getReferenceByKey(String key);
}
