package com.photographer.photoalbum.api;

import com.photographer.photoalbum.model.Photo;
import com.photographer.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photos")
public class PhotoRestController {
    @Autowired
    private PhotoRepository photoRepository;

    //servizio per lista foto
    @GetMapping
    public List<Photo> index() {
        return photoRepository.findAll();
    }

    //servizio detail photo
    @GetMapping("/{id}")
    public Photo get(@PathVariable Integer id) {
        Optional<Photo> photo = photoRepository.findById(id);
        if (photo.isPresent()) {
            return photo.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
