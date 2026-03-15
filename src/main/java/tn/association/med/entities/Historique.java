package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;
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

    @Enumerated(EnumType.STRING)
    private TypeAction action;

    private String entityName;   // Activite, Vote, Participation ...

    private Long entityId;       // id de l'entité

    private String description;

    private LocalDateTime dateAction;

    private Long idUser;

    @PrePersist
    public void prePersist(){
        this.dateAction = LocalDateTime.now();
    }
}