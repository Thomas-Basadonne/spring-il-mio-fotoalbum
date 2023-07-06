package com.photographer.photoalbum.service;

import com.photographer.photoalbum.exceptions.InvalidPhotoTitoloException;
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
        Optional<Photo> phtoOpt = photoRepository.findById(id);
        if (phtoOpt.isPresent()) {
            return phtoOpt.get();
        } else {
            throw new PhotoNotFoundExceptions("Photo con id " + id + " non trovata");
        }
    }

    //salva una pizza a partire da quella passata come param
    public Photo create(Photo photo) throws InvalidPhotoTitoloException {
        //valido nome unique
        if (!isUniqueTitolo(photo.getTitolo())) {
            throw new InvalidPhotoTitoloException(photo.getTitolo());
        }
        //creo nuova photo da salvare
        Photo photoToPersist = new Photo();
        //copio tt i campi
        photoToPersist.setTitolo(photo.getTitolo());
        photoToPersist.setDescrizione(photo.getDescrizione());
        photoToPersist.setUrl(photo.getUrl());
        photoToPersist.setVisibile(photo.isVisibile());
        photoToPersist.setCreatedAt(photo.getCreatedAt());
        photoToPersist.setCategories(photo.getCategories());
        //salvo la photo
        return photoRepository.save(photoToPersist);
    }

    private boolean isUniqueTitolo(String titolo) {
        List<Photo> result = photoRepository.findByTitolo(titolo);
        return result.isEmpty();
    }


}
