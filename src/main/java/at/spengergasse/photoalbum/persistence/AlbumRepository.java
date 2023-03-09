package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.Album;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.persistence.projections.Projections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, KeyHolderQueries<Album> {

    List<Projections.AlbumInfoDTO> findAllByNameLike(String name);

}
