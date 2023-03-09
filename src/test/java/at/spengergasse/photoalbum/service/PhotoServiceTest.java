package at.spengergasse.photoalbum.service;

import at.spengergasse.photoalbum.TestFixturesUnger;
import at.spengergasse.photoalbum.domain.Country;
import at.spengergasse.photoalbum.domain.Orientation;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.foundation.Base58;
import at.spengergasse.photoalbum.foundation.DateTimeFactory;
import at.spengergasse.photoalbum.persistence.PhotoRepository;
import at.spengergasse.photoalbum.persistence.PhotographerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static at.spengergasse.photoalbum.TestFixtures.uk;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class PhotoServiceTest {

    private @Mock PhotoRepository photoRepository;
    private @Mock PhotographerRepository photographerRepository;
    private @Mock DateTimeFactory dateTimeFactory;

    private PhotoService photoService;

    @BeforeEach
    void setup() {
        assumeThat(photoRepository).isNotNull();
        assumeThat(photographerRepository).isNotNull();
        assumeThat(dateTimeFactory).isNotNull();
        photoService = new PhotoService(photoRepository, photographerRepository, dateTimeFactory, new Base58());
    }

    @Test
    void ensureCreatePhotoFromExistingPhotographerWorksProperly() {

        //given
        String fileName = "fileName";
        String name = "name";
        Integer width = 480;
        Integer height = 640;
        String photographerKey = uk.getKey();
        LocalDateTime cutOffTS = LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 59, 59);
        Country austria = TestFixturesUnger.austria();
        Photographer uk = TestFixturesUnger.uk(austria);

        // train mocks
        when(photographerRepository.findByKey(photographerKey)).thenReturn(Optional.of(uk));
        when(photoRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(dateTimeFactory.now())
                .thenReturn(cutOffTS)
                .thenReturn(cutOffTS.plusSeconds(2));

        //when
        var photo = photoService.createPhoto(fileName, name, width, height, photographerKey);

        //then
        assertThat(photo).isNotNull();
        assertThat(photo.getFileName()).isEqualTo(fileName);
        assertThat(photo.getName()).isEqualTo(name);
        assertThat(photo.getWidth()).isEqualTo(width);
        assertThat(photo.getHeight()).isEqualTo(height);
        assertThat(photo.getCreationTS()).isEqualTo(cutOffTS);
        assertThat(photo.getPhotographer()).isEqualTo(uk);
        assertThat(photo.getOrientation()).isEqualTo(Orientation.PORTRAIT);

        verify(photographerRepository).findByKey(photographerKey);
        verifyNoMoreInteractions(photographerRepository);
        verify(dateTimeFactory, times(1)).now();
        verifyNoMoreInteractions(dateTimeFactory);
        verify(photoRepository).save(photo);
        verifyNoMoreInteractions(photoRepository);

    }

    @Test
    void ensureCreatePhotoHandlesDBErrorsProperly() {

        //given
        String fileName = "fileName";
        String name = "name";
        Integer width = 480;
        Integer height = 640;
        String photographerKey = uk.getKey();
        LocalDateTime cutOffTS = LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 59, 59);
        Country austria = TestFixturesUnger.austria();
        Photographer uk = TestFixturesUnger.uk(austria);

        // train mocks
        when(photographerRepository.findByKey(photographerKey)).thenThrow(new PersistenceException("network timeout!"));

        //expect
        var svcEx = assertThrows(ServiceException.class,
                () -> photoService.createPhoto(fileName, name, width, height, photographerKey));

        assertThat(svcEx).isNotNull();

        verify(photographerRepository).findByKey(photographerKey);
        verifyNoMoreInteractions(photographerRepository);
        verifyNoInteractions(dateTimeFactory);
        verifyNoInteractions(photoRepository);

    }

}