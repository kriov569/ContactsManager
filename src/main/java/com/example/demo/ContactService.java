package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository repository;

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return repository.findById(id);
    }

    public Contact saveContact(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(Long id, Contact contact) {
        if (repository.existsById(id)) {
            contact.setId(id);
            return repository.save(contact);
        } else {
            throw new RuntimeException("Contact not found");
        }
    }
}
