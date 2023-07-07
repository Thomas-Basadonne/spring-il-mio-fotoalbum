package com.photographer.photoalbum.security;

import com.photographer.photoalbum.model.User;
import com.photographer.photoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    //serve il repository per fare query su db nella tab users
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //recupero user da db
        Optional<User> result = userRepository.findByEmail(username);
        if (result.isPresent()) {
            return new DatabaseUserDetails(result.get());
        } else { //se non lo trovi lancia eccezione
            throw new UsernameNotFoundException("Utente con mail " + username + "non trovato");
        }
    }
}