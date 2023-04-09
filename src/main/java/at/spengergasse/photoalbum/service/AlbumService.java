package at.spengergasse.photoalbum.service;


import at.spengergasse.photoalbum.domain.Address;
import at.spengergasse.photoalbum.domain.Album;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.foundation.Base58;
import at.spengergasse.photoalbum.foundation.DateTimeFactory;
import at.spengergasse.photoalbum.persistence.AlbumRepository;
import at.spengergasse.photoalbum.service.commands.CreateAlbumCommand;
import at.spengergasse.photoalbum.service.commands.CreatePhotographerCommand;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)

public class AlbumService {

    private final AlbumRepository albumRepository;
    private final DateTimeFactory dateTimeFactory;

    private final Base58 base58;

    @Transactional
    public Album createAlbum(CreateAlbumCommand cmd){
        return createAlbum(
                cmd.name(),
                cmd.restricted());
    }
    @Transactional
    public Album createAlbum(String name, boolean restricted) {

        return Album.builder()
                        .key(base58.randomString(Album.KEY_LENGTH))
                        .name(name)
                        .restricted(restricted)
                        .creationTS(dateTimeFactory.now())
                        .build();
    }

    @Transactional(readOnly = false)
    public Optional<Album> updateAlbumName(String key, String newName) {
        return albumRepository.findByKey(key)
                .map(p -> {
                    p.setName(newName);
//                    return photoRepository.save(p);  //save ist nicht Muss, nicht Notwendig
                    return p;
                });
    }

    @Transactional(readOnly = false)
    public void updateAlbum(String Key, String name, boolean restricted) {
        albumRepository.findByKey(Key).ifPresent(p -> {
            p.setName(name);
            p.setRestricted(restricted);
        });
    }

    @Transactional(readOnly = false)
    public void deleteByKey(String key) {
        albumRepository.deleteByKey(key);
    }

    public Optional<Album> getAlbum(Long id) {

        return albumRepository.findById(id);
    }

    public Optional<Album> findByKey(String key) {

        return albumRepository.findByKey(key);
    }

    public void deleteKey(String key) {
        albumRepository.deleteByKey(key);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbum(String key) {
        return albumRepository.findByKey(key);
    }

}
