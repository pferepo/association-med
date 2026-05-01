package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;
import tn.association.med.enums.StatutProposition;
import tn.association.med.enums.TypeActivite;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ActiviteResponseDTO {
    private Long id;
    private String titre;
    private String description;
    private TypeActivite type;
    private String statut;
    private StatutProposition statutProposition;
    private LocalDateTime dateCreation;
    private LocalDateTime dateValidation;
    private List<String> membre; // emails des membres
    private String createur;     // nom + prenom concaténés
}