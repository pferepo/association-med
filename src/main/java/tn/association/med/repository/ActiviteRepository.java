package tn.association.med.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.Activite;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {

}
