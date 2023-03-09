package at.spengergasse.photoalbum.domain;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void ensureGetPhotoCountForWorksProperly() {

        //given
        Album album = new Album();
        Predicate<Photo> portraitPhotosWith480Width = (p) -> p.getWidth() == 480 && p.getOrientation() == Orientation.PORTRAIT;

        //when
        Long count = album.getPhotoCountFor(portraitPhotosWith480Width);

        //then
        assertThat(count).isEqualTo(0);
    }

}