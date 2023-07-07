package com.photographer.photoalbum.repository;

import com.photographer.photoalbum.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
}
