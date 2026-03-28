package tn.association.med.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.association.med.entities.Activite;
import tn.association.med.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	Optional<Vote> findByActivite(Activite activite);
}