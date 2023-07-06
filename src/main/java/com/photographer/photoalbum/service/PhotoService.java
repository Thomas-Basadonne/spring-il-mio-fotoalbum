package com.photographer.photoalbum.service;

import com.photographer.photoalbum.exceptions.PhotoNotFoundExceptions;
import com.photographer.photoalbum.model.Photo;
import com.photographer.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;


    //metodo che restituisce index Photo filtrate x param
    public List<Photo> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return photoRepository.findAll();
        } else return photoRepository.findByTitolo(keywordOpt.get());
    }


    //photo preso per id o restituisce eccezione
    public Photo getById(Integer id) throws PhotoNotFoundExceptions {
        Optional<Photo> pizzaOpt = photoRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            return pizzaOpt.get();
        } else {
            throw new PhotoNotFoundExceptions("Photo con id " + id + " non trovata");
        }
    }
}
