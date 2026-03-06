package tn.association.med.dto;

import lombok.Data;
import tn.association.med.enums.StatutActivite;

@Data
public class ActiviteRequestDTO {

    private String titre;
    private String description;
    private String type;

    private StatutActivite statut;
    private StatutActivite statutProposition;

}