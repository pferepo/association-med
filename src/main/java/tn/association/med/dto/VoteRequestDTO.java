package tn.association.med.dto;

import lombok.*;
import tn.association.med.enums.VoteStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequestDTO {

    private String description;

    private Date dateLimite;

    private VoteStatus statut; // optionnel, peut être null → @PrePersist gère le statut initial

    private Long activiteId;   // pour associer le vote à une activité existante
}