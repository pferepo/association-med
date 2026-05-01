package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.entities.ContactMessage;
import tn.association.med.serviceImpl.messages.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    // =====================
    // CREATE MESSAGE
    // =====================
    @PostMapping
    public ContactMessage save(@RequestBody ContactMessage msg) {
        return contactService.save(msg);
    }

    // =====================
    // GET ALL MESSAGES
    // =====================
    @GetMapping
    public List<ContactMessage> findAll() {
        return contactService.findAll();
    }

    // =====================
    // DELETE MESSAGE
    // =====================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
