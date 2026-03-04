package tn.association.med.dto;


import lombok.*;
import tn.association.med.enums.StatutActivite;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiviteResponseDTO {

    private Long id;
    private String titre;
    private String description;
    private String type;
    private StatutActivite statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateValidation;
}