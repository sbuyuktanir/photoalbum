package at.spengergasse.photoalbum.presentation.web;

import at.spengergasse.photoalbum.domain.Photo;
import at.spengergasse.photoalbum.domain.Photographer;
import at.spengergasse.photoalbum.service.PhotoService;
import at.spengergasse.photoalbum.service.PhotographerService;
import at.spengergasse.photoalbum.service.forms.AddPhotoForm;
import at.spengergasse.photoalbum.service.forms.AddPhotographerForm;
import at.spengergasse.photoalbum.service.forms.EditPhotoForm;
import at.spengergasse.photoalbum.service.forms.EditPhotographerForm;
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

@Controller
@RequestMapping(PhotographerController.BASE_ROUTE)

public class PhotographerController implements ControllerSupport{

    final static String BASE_ROUTE = "/photographers";
    final static String TEMPLATE_BASE_DIR = "photographers/";

    public final PhotographerService photographerService;

    @GetMapping
    public String getAllPhotographers(Model model) {
//Syntax Prüfung des Inputs , Input Verarbeitung, Daten Beschaffung, Logik
        List<Photographer> allPhotographers = photographerService.getAllPhotographers();

        if (allPhotographers.size() == 1) {
            model.addAttribute("photographer", allPhotographers.get(0));
            return template("detail");
        }

        model.addAttribute("photographers", allPhotographers);
        return template("index");   // return index.html

    }

    @GetMapping(KEY_PATH_VAR)
    public String getPhotographer(Model model, @PathVariable String key ) {

        Optional<Photographer> photographer = photographerService.getPhotographer(key);
        if (photographer.isEmpty()) return redirect(BASE_ROUTE);  //if no photo geh zurück BASE_ROUTE = Photos Tabelle
        model.addAttribute("photographer", photographer.get());
//        return "photos/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @GetMapping(KEY_PATH_VAR + "/row")
    public String getPhotographerAsRow(Model model, @PathVariable String key, HttpServletResponse response) {

        Optional<Photographer> photographer = photographerService.getPhotographer(key);

        if (photographer.isEmpty()) return redirect(BASE_ROUTE);  //if no photo geh zurück BASE_ROUTE = Photos Tabelle
        model.addAttribute("photographers", List.of(photographer.get()));

        response.setHeader("HX-Trigger", "showPhotographerRow");
//        return "photos/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("fragments : photographer-row");   // return detail.html
    }

    @GetMapping(ADD_PATH)
    public String showAddPhotographerForm(Model model) {
//        model.addAttribute("photographers", photographerService.getAllPhotographers());
        model.addAttribute("addPhotographerForm", new AddPhotographerForm());
        return template("addForm");
    }

    @PostMapping(ADD_PATH)
    public String handleAddPhotographerForm(Model model, @Valid @ModelAttribute AddPhotographerForm addPhotographerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("photographers", photographerService.getAllPhotographers());
            return template("addForm");
        }
        photographerService.createPhotographer(addPhotographerForm.getUserName(), addPhotographerForm.getFirstName(),
                addPhotographerForm.getLastName(), addPhotographerForm.getBillingAddressStreetNumber() ,
                addPhotographerForm.getBillingAddressZipCode() , addPhotographerForm.getBillingAddressCity() ,
                addPhotographerForm.getBillingAddressCountryIso2Code() );
        return redirect(BASE_ROUTE);
    }


    @GetMapping(EDIT_PATH)
    public String showEditPhotographerForm(Model model, @PathVariable String key) {
        Optional<Photographer> photographer= photographerService.findByKey(key);

        if (photographer.isPresent()) {
            Photographer p = photographer.get();
            model.addAttribute("key", key);
            model.addAttribute("editPhotographerForm", EditPhotographerForm.builder()
                    .userName(p.getUserName())
                    .firstName(p.getFirstName())
                    .lastName(p.getLastName())
                    .billingAddressCity(p.getBillingAddress().getCity())
                    .billingAddressStreetNumber(p.getBillingAddress().getStreetNumber())
                            .billingAddressZipCode(p.getBillingAddress().getZipCode())
                            .billingAddressCountryIso2Code(p.getBillingAddress().getCountry().getIso2Code())
                    .build());

            populatePhotographers(model);
            return template("editForm");
        } else {
            //logging
            return redirect(BASE_ROUTE);
        }
    }

    @PostMapping(EDIT_PATH)
    public String handleEditPhotographerForm(Model model, @PathVariable String key, @Valid @ModelAttribute EditPhotographerForm editPhotographerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            populatePhotographers(model);
            return template("editForm");
        }

        photographerService.updatePhotographer(key, editPhotographerForm.getUserName(), editPhotographerForm.getFirstName(),
                editPhotographerForm.getLastName(), editPhotographerForm.getBillingAddressCity(),
                editPhotographerForm.getBillingAddressStreetNumber(), editPhotographerForm.getBillingAddressZipCode(),
                editPhotographerForm.getBillingAddressCountryIso2Code());
        return redirect(BASE_ROUTE);
    }

    @DeleteMapping(value =KEY_PATH_VAR )
    @HxRequest
    @ResponseBody
    public HttpEntity<String> deletePhotographerHtmx(@PathVariable String key) {
        photographerService.deleteKey(key);
        return ResponseEntity.ok(null);
    }

    //    @GetMapping("/{key}/delete")
    @GetMapping(DELETE_PATH)
    public String deletePhotographer(@PathVariable String key) {

        photographerService.deleteByKey(key);
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
