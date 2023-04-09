package at.spengergasse.photoalbum.presentation.web;

import at.spengergasse.photoalbum.domain.Album;
import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.service.AlbumService;
import at.spengergasse.photoalbum.service.PhotoService;
import at.spengergasse.photoalbum.service.PhotographerService;
import at.spengergasse.photoalbum.service.forms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Controller
@RequestMapping(AlbumController.BASE_ROUTE)

public class AlbumController implements ControllerSupport{

    final static String BASE_ROUTE = "/albums";
    final static String TEMPLATE_BASE_DIR = "albums/";

    public final AlbumService albumService;

    @GetMapping
    public String getAllAlbums(Model model) {
//Syntax Prüfung des Inputs , Input Verarbeitung, Daten Beschaffung, Logik
        List<Album> allAlbums = albumService.getAllAlbums();
//        log.debug("Found {} photos in getAllAlbums()", allAlbums.size());

        if (allAlbums.size() == 1) {
            model.addAttribute("album", allAlbums.get(0));
            return template("detail");
        }
        model.addAttribute("albums", allAlbums);
        return template("index");   // return index.html
//        return "photos/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
    }

    @GetMapping(KEY_PATH_VAR)
    public String getAlbum(Model model, @PathVariable String key ) {

        Optional<Album> album = albumService.getAlbum(key);
        if (album.isEmpty()) return redirect(BASE_ROUTE);  //if no photo geh zurück BASE_ROUTE = Photos Tabelle
        model.addAttribute("album", album.get());
//        return "photos/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @GetMapping(KEY_PATH_VAR + "/row")
    public String getAlbumAsRow(Model model, @PathVariable String key, HttpServletResponse response) {

        Optional<Album> album = albumService.getAlbum(key);

        if (album.isEmpty()) return redirect(BASE_ROUTE);  //if no photo geh zurück BASE_ROUTE = Photos Tabelle
        model.addAttribute("albums", List.of(album.get()));

        response.setHeader("HX-Trigger", "showAlbumRow");
//        return "photos/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("fragments : album-row");   // return detail.html
    }

    @GetMapping(ADD_PATH)
    public String showAddAlbumForm(Model model) {
//        model.addAttribute("photographers", photographerService.getAllPhotographers());
        model.addAttribute("addAlbumForm", new AddAlbumForm());
        return template("addForm");
    }

    @PostMapping(ADD_PATH)
    public String handleAddAlbumForm(Model model, @Valid @ModelAttribute AddAlbumForm addAlbumForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("photographers", photographerService.getAllPhotographers());
            return template("addForm");
        }
        albumService.createAlbum(addAlbumForm.getName(), addAlbumForm.getRestricted());
        return redirect(BASE_ROUTE);
    }


    @GetMapping(EDIT_PATH)
    public String showEditAlbumForm(Model model, @PathVariable String key) {
        Optional<Album> album= albumService.findByKey(key);

        if (album.isPresent()) {
            Album p = album.get();
            model.addAttribute("key", key);
            model.addAttribute("editAlbumForm", EditAlbumForm.builder()
                    .name(p.getName())
                    .restricted(p.getRestricted())
                    .build());

            return template("editForm");
        } else {
            //logging
            return redirect(BASE_ROUTE);
        }
    }

    @PostMapping(EDIT_PATH)
    public String handleEditAlbumForm(Model model, @PathVariable String key, @Valid @ModelAttribute EditAlbumForm editAlbumForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template("editForm");
        }

        albumService.updateAlbum(key, editAlbumForm.getName(), editAlbumForm.isRestricted());
        return redirect(BASE_ROUTE);
    }

    @DeleteMapping(value =KEY_PATH_VAR )
    @HxRequest
    @ResponseBody
    public HttpEntity<String> deleteAlbumHtmx(@PathVariable String key) {
        albumService.deleteKey(key);
        return ResponseEntity.ok(null);
    }

    //    @GetMapping("/{key}/delete")
    @GetMapping(DELETE_PATH)
    public String deleteAlbum(@PathVariable String key) {

        albumService.deleteByKey(key);
        return redirect(BASE_ROUTE);
    }


    /*
@GetMapping
public String getAllProducts(Model model) {

    List<Product> products = productService.getAllProducts();
    log.debug("Found {} products in getAllProducts()", products.size());
    model.addAttribute("products", products);
//        return "reservations/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
    return template("index");   // return index.html
}
*/

    @Override
    public String getTemplateBaseDir() {
        return TEMPLATE_BASE_DIR;
    }
}
