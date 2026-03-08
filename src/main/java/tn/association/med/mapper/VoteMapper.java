package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.Vote;

@Component
public class VoteMapper {

    public VoteResponseDTO toDto(Vote vote) {

        return VoteResponseDTO.builder()
                .id(vote.getId())
                .dateLimite(vote.getDateLimite())
                .statut(vote.getStatut())
                .activiteId(vote.getActivite().getId())
                .build();
    }
}