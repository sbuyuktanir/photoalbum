package at.spengergasse.photoalbum.presentation.api;

import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.persistence.PhotoRepository;
import at.spengergasse.photoalbum.persistence.PhotographerRepository;
import at.spengergasse.photoalbum.service.commands.CreatePhotoCommand;
import at.spengergasse.photoalbum.service.PhotoService;
import at.spengergasse.photoalbum.service.dtos.PhotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RequiredArgsConstructor

@RestController
// @RequestMapping( "/api/photos")  //ApiConstants altinda API = "/api" diye eslestirdi.
// public class ApiConstants {public static final String API = "/api";}
@RequestMapping(ApiConstants.API+"/photos")

public class PhotoRestController {

   private final PhotoService photoService;
   private final PhotoRepository photoRepository;
   private final PhotographerRepository photographerRepository;

   /*
   @GetMapping({ "", "/"})
    public HttpEntity<List<PhotoDto>> getAllPhotos() {
       return ResponseEntity.ok(photoService
               .getAllPhotos()
               .stream()
               .map(this::toDto)
               .toList());
   }
*/

    @GetMapping({ "", "/"})  //?expand=photographer; photographer.address; taggedPersons
    public HttpEntity<CollectionModel<PhotoDto>> getAllPhotos() {
        List<PhotoDto> dtoList = photoService.getAllPhotos().stream().map(this::toDto).toList();
       if (dtoList.isEmpty()) return ResponseEntity.noContent().build();
       CollectionModel<PhotoDto> photoDtos = CollectionModel.of(dtoList).add(createCollectionLink());
        return ResponseEntity.ok(photoDtos);
    }

    @PostMapping( { "", "/"} )
    public HttpEntity<PhotoDto> createPhoto(@RequestBody CreatePhotoCommand cmd) {

       Photo photo = photoService.createPhoto(cmd);
       PhotoDto dto = toDto(photo);
       Link _self = dto.getLink(IanaLinkRelations.SELF).get();

//       URI location = linkTo(methodOn(PhotoRestController.class).getPhoto(photo.getKey())).withSelfRel().toUri();

       return ResponseEntity.created(_self.toUri()).body(dto);
   }

/*
    @PostMapping( { "", "/"} )
    public HttpEntity<PhotoDto> createPhoto(@RequestBody CreatePhotoCommand createPhotoCommand) {

        return Optional.ofNullable(photoService.createPhoto(createPhotoCommand.fileName(),
                        createPhotoCommand.name(),
                        createPhotoCommand.width(),
                        createPhotoCommand.height(),
                        createPhotoCommand.photographerKey()))
                .map(PhotoDto::new)
                .map(dto -> ResponseEntity.created(createPhotoUri(dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());
    }
*/

   @GetMapping( "/{key}")
   public HttpEntity<PhotoDto> getPhoto(@PathVariable String key) {
       return photoService.findByKey(key)
               .map(this::toDto)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }

    @PatchMapping( "/{key}")
    public HttpEntity<PhotoDto> updatePhotoName(@PathVariable String key, @RequestParam String name) {
        return photoService.updatePhotoName(key, name)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected PhotoDto toDto (Photo photo) {
       var _self = linkTo(methodOn(PhotoRestController.class).getPhoto(photo.getKey()));
        return new PhotoDto(photo)
                .add(_self.withSelfRel())
                .add(_self.slash("photographer").withRel("photographer-nested"))
                .add(_self.slash("taggedPersons").withRel("taggedPersons"))
                .add(linkTo(methodOn(PhotographerRestController.class).getPhotographer(photo.getPhotographer().getKey())).withRel("photographer"))
                .add(createCollectionLink());
    }

    private Link createCollectionLink() {
        return linkTo(methodOn(PhotoRestController.class).getAllPhotos()).withRel(IanaLinkRelations.COLLECTION);
    }

/*
   private URI createPhotoUri(PhotoDto dto) {
       try {
           String key = URLEncoder.encode(dto.photoName(), StandardCharsets.UTF_8);
           return new URI("/api/photos/%s".formatted(key));
       } catch (URISyntaxException uriSyntaxEx) {
           throw new RuntimeException(uriSyntaxEx);
       }
   }
*/

}


