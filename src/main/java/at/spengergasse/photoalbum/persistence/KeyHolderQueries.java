package at.spengergasse.photoalbum.persistence;

import at.spengergasse.photoalbum.domain.KeyHolder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Optional;

public interface KeyHolderQueries<T extends KeyHolder> {

    Optional<T> findByKey(String key);

    void deleteByKey(String key);
}
