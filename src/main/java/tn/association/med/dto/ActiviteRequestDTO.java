package tn.association.med.dto;

import java.util.List;

import lombok.Data;
import tn.association.med.enums.StatutActivite;
import tn.association.med.enums.TypeActivite;

@Data
public class ActiviteRequestDTO {

    private String titre;
    private String description;
    private TypeActivite type;
    private List<String> membre;
    private StatutActivite statut;
    private StatutActivite statutProposition;

}