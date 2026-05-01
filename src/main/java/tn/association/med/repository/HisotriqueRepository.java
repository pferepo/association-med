package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.association.med.entities.Historique;

import java.util.List;

public interface HisotriqueRepository extends JpaRepository<Historique, Long> {

    List<Historique> findAllByOrderByDateActionDesc();
}