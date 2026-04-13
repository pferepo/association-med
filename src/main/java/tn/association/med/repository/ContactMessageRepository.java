package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

}