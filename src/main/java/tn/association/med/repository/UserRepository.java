package tn.association.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.association.med.entities.User;
import tn.association.med.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

}