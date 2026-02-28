package tn.association.med.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}