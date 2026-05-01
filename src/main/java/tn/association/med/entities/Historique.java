package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;
import tn.association.med.enums.HistoriqueStatus;
import tn.association.med.enums.TypeAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "historiques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Historique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type d'action (VOTE, ACTIVITE, USER, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAction action;

    // Nom de l'entité concernée
    @Column(nullable = false)
    private String entityName;

    // ID de l'entité concernée
    @Column(nullable = false)
    private Long entityId;

    // Message lisible pour UI (IMPORTANT pour frontend)
    @Column(length = 1000)
    private String description;

    // Qui a fait l'action
    @Column(nullable = false)
    private Long idUser;

    // Date de l'action
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateAction;

    @Enumerated(EnumType.STRING)
    private HistoriqueStatus status;


    private String context;

    @PrePersist
    public void prePersist() {
        this.dateAction = LocalDateTime.now();

        // default status si non fourni
        if (this.status == null) {
            this.status = HistoriqueStatus.INFO;
        }
    }
}