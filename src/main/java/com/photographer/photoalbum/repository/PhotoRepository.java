package com.photographer.photoalbum.repository;

import com.photographer.photoalbum.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    //ricerca foto x titolo
    List<Photo> findByTitolo(String titolo);
}
