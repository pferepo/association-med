package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.association.med.entities.Historique;

public interface HisotriqueRepository extends JpaRepository<Historique, Long> {
	
}