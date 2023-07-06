package com.photographer.photoalbum.controller;

import com.photographer.photoalbum.model.Photo;
import com.photographer.photoalbum.repository.CategoryRepository;
import com.photographer.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    //metodo custom x selezionare pizza da db o tirare un eccezione
    private Photo getPhotoById(Integer id) {
        //verifico se esite pizza con quel id
        Optional<Photo> result = photoRepository.findById(id);
        // if not => 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo con id " + id + " non trovata");
        }
        return result.get();
    }
}
