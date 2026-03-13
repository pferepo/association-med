package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;
import tn.association.med.enums.StatutActivite;
import tn.association.med.enums.TypeActivite;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "activites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeActivite type;

    @Enumerated(EnumType.STRING)
    private StatutActivite statut;

    @Enumerated(EnumType.STRING)
    private StatutActivite statutProposition; 

    private LocalDateTime dateCreation;

    private LocalDateTime dateValidation;
    
    private List<String> membres;
    
    
    

    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutActivite.EN_ATTENTE;
    }
}