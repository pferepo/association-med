package tn.association.med.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.entities.Activite;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {

	@Query("SELECT a FROM Activite a WHERE a.type IN ('FORMATION','EVENEMENTS')")
	Collection<Activite> getActivitiesInvite();

}