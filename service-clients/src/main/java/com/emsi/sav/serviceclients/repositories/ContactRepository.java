package com.emsi.sav.serviceclients.repositories;

import com.emsi.sav.serviceclients.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}