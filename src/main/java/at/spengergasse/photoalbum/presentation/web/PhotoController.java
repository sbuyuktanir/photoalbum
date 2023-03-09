package at.spengergasse.photoalbum.presentation.web;

import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.service.PhotoService;
import at.spengergasse.photoalbum.service.PhotographerService;
import at.spengergasse.photoalbum.service.forms.AddPhotoForm;
import at.spengergasse.photoalbum.service.forms.EditPhotoForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Controller
@RequestMapping(PhotoController.BASE_ROUTE)

public class PhotoController implements ControllerSupport{

    final static String BASE_ROUTE = "/photos";
    final static String TEMPLATE_BASE_DIR = "photos/";

    public final PhotoService photoService;
    public final PhotographerService photographerService;

    @GetMapping
    public String getAllPhotos(Model model) {
//Syntax Prüfung des Inputs , Input Verarbeitung, Daten Beschaffung, Logik
        List<Photo> photos = photoService.getAllPhotos();
        log.debug("Found {} photos in getAllPhotos()", photos.size());
        if (photos.size() == 1) {
            model.addAttribute("photo", photos.get(0));
            return template("detail");
        }
        model.addAttribute("photos", photos);
        return template("index");   // return index.html
//        return "photos/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
    }

    @GetMapping(KEY_PATH_VAR)
    public String getPhoto(@PathVariable String key, Model model) {

        Optional<Photo> photo = photoService.findByKey(key);
        if (photo.isEmpty()) return redirect(BASE_ROUTE);  //if no photo geh zurück BASE_ROUTE = Photos Tabelle
        model.addAttribute("photo", photo.get());
//        return "photos/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @GetMapping(ADD_PATH)
    public String showAddPhotoForm(Model model) {
        populatePhotographers (model);
//        model.addAttribute("photographers", photographerService.getAllPhotographers());
        model.addAttribute("addPhotoForm", new AddPhotoForm());
        return template("addForm");
    }

    @PostMapping(ADD_PATH)
    public String handleAddPhotoForm(Model model, @Valid @ModelAttribute AddPhotoForm addPhotoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("photographers", photographerService.getAllPhotographers());
            populatePhotographers(model);
            return template("addForm");
        }
        photoService.createPhoto(addPhotoForm.getFileName(), addPhotoForm.getName(),
                addPhotoForm.getWidth(), addPhotoForm.getHeight(), addPhotoForm.getPhotographerKey());
        return redirect(BASE_ROUTE);
    }

    @GetMapping(EDIT_PATH)
    public String showEditPhotoForm(Model model, @PathVariable String key) {
        Optional<Photo> photo= photoService.findByKey(key);

        if (photo.isPresent()) {
            Photo p = photo.get();
                    model.addAttribute("key", key);
                    model.addAttribute("editPhotoForm", EditPhotoForm.builder()
                    .name(p.getName())
                    .fileName(p.getFileName())
                    .width(p.getWidth())
                    .height(p.getHeight())
                    .photographerKey(p.getPhotographer().getKey())
                    .build());

            populatePhotographers(model);
            return template("editForm");
        } else {
            //logging
            return redirect(BASE_ROUTE);
        }
    }

    @PostMapping(EDIT_PATH)
    public String handleEditPhotoForm(Model model, @PathVariable String key, @Valid @ModelAttribute EditPhotoForm editPhotoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            populatePhotographers(model);
            return template("editForm");
        }

        photoService.updatePhoto(key, editPhotoForm.getFileName(), editPhotoForm.getName(),
                editPhotoForm.getWidth(), editPhotoForm.getHeight(), editPhotoForm.getPhotographerKey());
        return redirect(BASE_ROUTE);
    }

//    @GetMapping("/{key}/delete")
    @GetMapping(DELETE_PATH)
    public String deletePhoto(@PathVariable String key) {

    photoService.deleteByKey(key);
    return redirect(BASE_ROUTE);
    }

    private void populatePhotographers(Model model) {
        model.addAttribute("photographers", photographerService.getAllPhotographers());
    }

    @Override
    public String getTemplateBaseDir() {
        return TEMPLATE_BASE_DIR;
    }
}
