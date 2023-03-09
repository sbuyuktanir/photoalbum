package at.spengergasse.photoalbum.presentation.api;

import at.spengergasse.photoalbum.service.PhotographerService;
import at.spengergasse.photoalbum.service.commands.CreatePhotographerCommand;
import at.spengergasse.photoalbum.service.dtos.PhotoDto;
import at.spengergasse.photoalbum.service.dtos.PhotographerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@RestController
// @RequestMapping( "/api/photographers")  //ApiConstants altinda API = "/api" diye eslestirdi.
// public class ApiConstants {public static final String API = "/api";}
@RequestMapping(ApiConstants.API+"/photographers")

public class PhotographerRestController {

   private final PhotographerService photographerService;

    @GetMapping({ "", "/"})
    public HttpEntity<List<PhotographerDto>> getAllPhotographers() {
        return ResponseEntity.ok(photographerService
                .getAllPhotographers()
                .stream()
                .map(PhotographerDto::new)
                .toList());
    }

/*
   @PostMapping( { "", "/"} )
    public HttpEntity<PhotographerDto> createPhotographer(@Valid @RequestBody CreatePhotographerCommand cmd) {

        Photographer photographer = photographerService.createPhotographer(cmd);
        URI location = linkTo(methodOn(PhotographerRestController.class).getPhotographer(photographer.getKey())).withSelfRel().toUri();
        PhotographerDto dto = new PhotographerDto(photographer);
        return ResponseEntity.created(location).body(dto);

   }
*/

    @PostMapping( { "", "/"} )
    public HttpEntity<PhotographerDto> createPhotographer(@RequestBody CreatePhotographerCommand createPhotographerCommand) {

        return Optional.ofNullable(photographerService.createPhotographer(createPhotographerCommand.userName(),
                        createPhotographerCommand.firstName(),
                        createPhotographerCommand.lastName(),
                        createPhotographerCommand.billingAddressStreetNumber(),
                        createPhotographerCommand.billingAddressZipCode(),
                        createPhotographerCommand.billingAddressCity(),
                        createPhotographerCommand.billingAddressCountryIso2Code()))
                .map(PhotographerDto::new)
                .map(dto -> ResponseEntity.created(createPhotographerUri(dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());
    }

   @GetMapping("/{key}")
    public HttpEntity<PhotographerDto> getPhotographer(@PathVariable String key) {
       return photographerService.getPhotographer(key)
               .map(PhotographerDto::new)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }

    private URI createPhotographerUri(PhotographerDto dto) {
        try {
            String key = URLEncoder.encode(dto.userName(), StandardCharsets.UTF_8);
            return new URI("/api/photographers/%s".formatted(key));
        } catch (URISyntaxException uriSyntaxEx) {
            throw new RuntimeException(uriSyntaxEx);
        }
    }

}


