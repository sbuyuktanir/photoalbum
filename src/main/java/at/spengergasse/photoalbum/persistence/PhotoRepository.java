package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.persistence.projections.Projections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>, KeyHolderQueries<Photo> {

    List<Photo> findAllByCreationTSBetween(LocalDateTime startTS, LocalDateTime endTS);

    List<Projections.PhotoInfoDTO> findAllByNameLike(String name);

    Optional<Photo> findByName(String name);

}
