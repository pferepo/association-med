package tn.association.med.serviceImpl.messages;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.entities.ContactMessage;
import tn.association.med.repository.ContactMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository repository;

    public ContactMessage save(ContactMessage msg) {
        return repository.save(msg);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Message introuvable");
        }
        repository.deleteById(id);
    }

    public List<ContactMessage> findAll() {
        return repository.findAll();
    }
}