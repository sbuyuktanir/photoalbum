package at.spengergasse.photoalbum.service.dtos;

import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.service.PhotoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Relation(collectionRelation = "photos", itemRelation = "photo")
public class PhotoDto extends RepresentationModel<PhotoDto> {

        private final String fileName;
        private final String photoName;
        private final LocalDateTime createdAt;
        private final Integer width;
        private final Integer height;
        private final String orientation;
        private final String photographer;
        private final PhotographerDto photographerDto;

    public PhotoDto(Photo photo) {
        this(photo.getFileName(), photo.getName(), photo.getCreationTS(),
                photo.getWidth(), photo.getHeight(), photo.getOrientation().name(),
                photo.getPhotographer().getDisplayName(),new PhotographerDto(photo.getPhotographer()));

    }
}
