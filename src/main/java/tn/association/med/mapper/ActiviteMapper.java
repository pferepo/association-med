package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
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
                .membres(dto.getMembre())   // List<String>
                .build();
    }

    public ActiviteResponseDTO toDto(Activite activite) {
        String createurNomPrenom = null;
        if (activite.getCreateur() != null) {
            createurNomPrenom = activite.getCreateur().getNom() + " " + activite.getCreateur().getPrenom();
        }

        return ActiviteResponseDTO.builder()
                .id(activite.getId())
                .titre(activite.getTitre())
                .description(activite.getDescription())
                .type(activite.getType())
                .statut(String.valueOf(activite.getStatut()))
                .statutProposition(activite.getStatutProposition())
                .dateCreation(activite.getDateCreation())
                .dateValidation(activite.getDateValidation())
                .membre(activite.getMembres()) // List<String>
                .createur(createurNomPrenom)
                .build();
    }
}