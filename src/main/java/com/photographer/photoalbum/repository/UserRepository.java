package com.photographer.photoalbum.repository;

import com.photographer.photoalbum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //recupera uno user a partire dallo username(email)
    Optional<User> findByEmail(String email);
}
