package com.example.demo;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository repository;

    @Autowired
    private ContactDao contactDao;

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

    public void loadContactsFromCSV(InputStream csvInput) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(csvInput))) {
            List<Contact> contacts = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Contact contact = new Contact();
                contact.setFirstName(line[0]);
                contact.setLastName(line[1]);
                contact.setPhoneNumber(line[2]);
                contact.setEmail(line[3]);
                contacts.add(contact);
            }
            contactDao.batchInsert(contacts);
        }
    }
}
