package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.ParticipationVote;

public interface ParticipationVoteRepository extends JpaRepository<ParticipationVote, Long> {
}