package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.ParticipationVoteResponseDTO;
import tn.association.med.entities.ParticipationVote;

@Component
public class ParticipationVoteMapper {

    public ParticipationVoteResponseDTO toDto(ParticipationVote vote) {

        return ParticipationVoteResponseDTO.builder()
                .id(vote.getId())
                .choix(vote.getChoix())
                .dateVote(vote.getDateVote())
                .utilisateurId(vote.getUtilisateur().getId())
                .voteId(vote.getVote().getId())
                .build();
    }
}