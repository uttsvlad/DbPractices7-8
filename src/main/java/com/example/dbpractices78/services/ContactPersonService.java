package com.example.dbpractices78.services;

import com.example.dbpractices78.models.ContactPerson;
import com.example.dbpractices78.repositories.ContactPersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Utts
 */
@Service
public class ContactPersonService {
    private final ContactPersonRepository contactPersonRepository;

    public ContactPersonService(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    public List<ContactPerson> findAll() {
        return contactPersonRepository.findAll();
    }
}
