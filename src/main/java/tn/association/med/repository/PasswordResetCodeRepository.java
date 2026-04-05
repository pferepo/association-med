package tn.association.med.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.association.med.entities.PasswordResetCode;

import java.util.Optional;

public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {

    Optional<PasswordResetCode> findTopByEmailAndCodeOrderByExpirationDesc(String email, String code);

    @Transactional
    @Modifying
    @Query("DELETE FROM PasswordResetCode p WHERE p.email = :email")
    void deleteAllByEmail(@Param("email") String email);
}