package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;
import tn.association.med.enums.StatutActivite;

import java.time.LocalDateTime;

@Data
@Builder
public class ActiviteResponseDTO {

    private Long id;
    private String titre;
    private String description;
    private String type;

    private StatutActivite statut;
    private StatutActivite statutProposition;

    private LocalDateTime dateCreation;
    private LocalDateTime dateValidation;
}