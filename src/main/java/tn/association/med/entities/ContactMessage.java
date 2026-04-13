package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String objectif;

    @Column(length = 2000)
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
}