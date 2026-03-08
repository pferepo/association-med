package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;
import tn.association.med.entities.User;
import tn.association.med.enums.StatutActivite;
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
    private List<String> membre;
    private StatutActivite statut;
    private StatutActivite statutProposition;

    private LocalDateTime dateCreation;
    private LocalDateTime dateValidation;
}