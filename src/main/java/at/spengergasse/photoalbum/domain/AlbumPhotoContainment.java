package at.spengergasse.photoalbum.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// @Builder

public class AlbumPhotoContainment extends AbstractPersistable<Long> {

    private Album album;
    private Photo photo;
    private LocalDateTime assignmentTS;

}

