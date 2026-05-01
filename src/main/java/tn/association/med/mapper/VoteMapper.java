package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.VoteRequestDTO;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Vote;

@Component
public class VoteMapper {

    // -------------------- toEntity --------------------
    public Vote toEntity(VoteRequestDTO dto, Activite activite) {
        if (dto == null) return null;

        return Vote.builder()
                .description(dto.getDescription())
                .dateLimite(dto.getDateLimite())
                .statut(dto.getStatut())          // VoteStatus enum
                .activite(activite)               // injecté depuis le service
                // dateCreation et statut initial sont gérés par @PrePersist
                .build();
    }

    // -------------------- toDto --------------------
    public VoteResponseDTO toDto(Vote vote) {
        if (vote == null) return null;

        return VoteResponseDTO.builder()
                .id(vote.getId())
                .description(vote.getDescription())
                .dateLimite(vote.getDateLimite())
                .statut(vote.getStatut())         // VoteStatus enum
                .activiteId(vote.getActivite() != null ? vote.getActivite().getId() : null)
                .dateCreation(vote.getDateCreation())
                .build();
    }

    // -------------------- updateEntity (optionnel) --------------------
    public void updateEntity(Vote vote, VoteRequestDTO dto, Activite activite) {
        if (vote == null || dto == null) return;

        vote.setDescription(dto.getDescription());
        vote.setDateLimite(dto.getDateLimite());
        vote.setActivite(activite);

        // Mettre à jour le statut seulement si fourni
        if (dto.getStatut() != null) {
            vote.setStatut(dto.getStatut());
        }
    }
}