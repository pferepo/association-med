package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.*;
import tn.association.med.entities.Activite;

@Component
public class ActiviteMapper {

    public Activite toEntity(ActiviteRequestDTO dto) {
        return Activite.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .type(dto.getType())
                .statut(dto.getStatut())
                .statutProposition(dto.getStatutProposition())
                .build();
    }

    public ActiviteResponseDTO toDto(Activite activite) {
        return ActiviteResponseDTO.builder()
                .id(activite.getId())
                .titre(activite.getTitre())
                .description(activite.getDescription())
                .type(activite.getType())
                .statut(activite.getStatut())
                .statutProposition(activite.getStatutProposition())
                .dateCreation(activite.getDateCreation())
                .dateValidation(activite.getDateValidation())
                .build();
    }
}