package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}