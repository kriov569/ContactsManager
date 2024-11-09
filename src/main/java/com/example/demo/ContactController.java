package com.example.demo;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.channels.MulticastChannel;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
@Validated
public class ContactController {
    @Autowired
    private ContactService service;

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = service.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact savedContact = service.saveContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact) {
        try {
            Contact updatedContact = service.updateContact(id, contact);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file")MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            service.loadContactsFromCSV(inputStream);
            return ResponseEntity.ok("Контакты успешно загружены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки файла");
        }
    }
}
