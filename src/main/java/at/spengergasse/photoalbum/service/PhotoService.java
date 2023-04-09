package at.spengergasse.photoalbum.service;


import at.spengergasse.photoalbum.domain.Orientation;
import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.foundation.Base58;
import at.spengergasse.photoalbum.foundation.DateTimeFactory;
import at.spengergasse.photoalbum.persistence.PhotoRepository;
import at.spengergasse.photoalbum.persistence.PhotographerRepository;
import at.spengergasse.photoalbum.service.commands.CreatePhotoCommand;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.MemberSubstitution;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotographerRepository photographerRepository;
//    private final SimpleJpaRepository simpleJpaRepository;
    private final DateTimeFactory dateTimeFactory;
    private final Base58 keyGen;
//    private final Ensurer ensurer;

    @Transactional(readOnly = false)  //default false
    public Photo createPhoto(CreatePhotoCommand cmd) {
        return createPhoto(cmd.fileName(),
                cmd.name(),
                cmd.width(),
                cmd.height(),
                cmd.photographerKey());
    }

    @Transactional(readOnly = false)  //default false
    public Photo createPhoto(String fileName, String name, Integer width, Integer height, String photographerKey) {

        try {
            return photographerRepository.findByKey(photographerKey)
                    .map(photographer -> Photo.builder()
                            .key(keyGen.randomString(Photo.KEY_LENGTH))
                            .fileName(fileName)
                            .name(name)
//                        .creationTS(LocalDateTime.now())  //bunu begenmiyor.
                            .creationTS(dateTimeFactory.now())
                            .width(width)
                            .height(height)
                            .photographer(photographer)
                            .orientation(Orientation.determine(width, height))
                            .build())
                    .map(photoRepository::save)
                    .orElseThrow();
        } catch (PersistenceException sqlEx) {
            throw ServiceException.forEntity(Photo.class.getSimpleName(), sqlEx);
        }
    }

    public List<Photo> getAllPhotos() {

        return photoRepository.findAll();
    }

    public Optional<Photo> findPhotoByName(String name) {

        return photoRepository.findByName(name);
    }

    public Optional<Photo> findByKey(String key) {

        return photoRepository.findByKey(key);
    }

    @Transactional(readOnly = false)
    public Optional<Photo> updatePhotoName(String key, String newName) {
        return photoRepository.findByKey(key)
                .map(p -> {
                    p.setName(newName);
//                    return photoRepository.save(p);  //save ist nicht Muss, nicht Notwendig
                    return p;
                });
    }

    @Transactional(readOnly = false)
    public void deleteByKey(String key) {
        photoRepository.deleteByKey(key);
    }

    @Transactional(readOnly = false)
    public void updatePhoto(String photoKey, String fileName, String name, Integer width, Integer height, String photographerKey) {
        photoRepository.findByKey(photoKey).ifPresent(p -> {
            p.setName(name);
            p.setFileName(fileName);
            p.setWidth(width);
            p.setHeight(height);
            p.setPhotographer(photographerRepository.getReferenceByKey(photographerKey));
        });
    }

    public Optional<Photo> getPhoto(String key) {
        return photoRepository.findByKey(key);
    }

    public void deleteKey(String key) {
        photoRepository.deleteByKey(key);
    }
}
