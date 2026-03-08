package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}