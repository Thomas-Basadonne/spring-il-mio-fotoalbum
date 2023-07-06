package com.photographer.photoalbum.controller;

import com.photographer.photoalbum.messages.AlertMessage;
import com.photographer.photoalbum.messages.AlertMessageType;
import com.photographer.photoalbum.model.Photo;
import com.photographer.photoalbum.repository.CategoryRepository;
import com.photographer.photoalbum.repository.PhotoRepository;
import com.photographer.photoalbum.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PhotoService photoService;

    //metodo x la index delle foto
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Photo> photo;
        // se non ho il param fai la ricerca all
        if (searchString == null || searchString.isBlank()) {
            photo = photoRepository.findAll();
        } else {
            // altrimenti ricerca con param
            photo = photoRepository.findByTitolo(searchString);
        }
        model.addAttribute("photoList", photo);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        if (photo.isEmpty()) {
            model.addAttribute("noPhotoMessage", "Non ci sono photo disponibili al momento.");
        }
        return "/photos/index";
    }

    //metodo x il detail delle foto
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer photoId, Model model) {
        //cerca photo con quell'id
        Photo photo = getPhotoById(photoId);
        //passa photo a view
        model.addAttribute("photo", photo);
        return "/photos/detail";
    }

    //Controller che gestisce la pagina create
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());
        // add lista ingredienti x checkbox
        model.addAttribute("categoryList", categoryRepository.findAll());

        //return "/photo/create";
        return "/photos/form"; // template unico create/update
    }

    //Controller che gestisce il form della pagina create
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo photoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //dati photo sono nell'oggetto photoForm
        // controllo dati in validazione
        if (!isUniqueTitolo(photoForm)) {
            //aggiungo a mano errore in BindingResult
            bindingResult.addError(new FieldError("photo", "name", photoForm.getTitolo(), false, null, null, "Il nome deve essere unico"));
        }
        if (bindingResult.hasErrors()) {
            //ci sono errori!
            //rigenero form con dati photo pre caricati
            // add lista ingredienti x checkbox
            model.addAttribute("ingredientList", categoryRepository.findAll());
            return "/photos/form";
        }
        System.out.println(photoForm.getCategories());
        //persisto photoForm, metodo save fa un create sql se non esiste oggetto con stessa pk se no update
        photoRepository.save(photoForm);
        //redirect home se va tutto bene
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Photo aggiunta alla lista!"));
        return "redirect:/photos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Photo photo = getPhotoById(id);
        //recupero dati della photo e la aggiungo al model
        model.addAttribute("photo", photo);
        // add lista ingredienti x checkbox
        model.addAttribute("ingredientList", categoryRepository.findAll());
        return "/photos/form"; // template unico create/update
    }


    @PostMapping("edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("photo") Photo photoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Photo photoToEdit = getPhotoById(id);//photo prima di essere modificata
        //valido photoForm
        //se i nomi sono diversi dai errore
        if (!photoToEdit.getTitolo().equals(photoForm.getTitolo()) && !isUniqueTitolo(photoForm)) {
            bindingResult.addError(new FieldError("photo", "name", photoForm.getTitolo(), false, null, null, "Il titolo deve essere unico"));
        }
        if (bindingResult.hasErrors()) {
            // add lista ingredienti x checkbox
            model.addAttribute("ingredientList", categoryRepository.findAll());
            return "photos/form";
        }
        //trasferisco dati che non sono presenti nel form x non perderli
        photoForm.setId(photoToEdit.getId());
        //edit e salvataggio dati
        photoRepository.save(photoForm);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Photo modificata con successo!"));
        return "redirect:/photos";
    }


    //metodo custom x controllo name unique nel db
    private boolean isUniqueTitolo(Photo photoForm) {
        List<Photo> result = photoRepository.findByTitolo(photoForm.getTitolo());
        return result.isEmpty(); // Restituisce true se il titolo non esiste, false se esiste già
    }


    //metodo custom x selezionare photo da db o tirare un eccezione
    private Photo getPhotoById(Integer id) {
        //verifico se esite photo con quel id
        Optional<Photo> result = photoRepository.findById(id);
        // if not => 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo con id " + id + " non trovata");
        }
        return result.get();
    }
}
