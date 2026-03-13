package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationVoteResponseDTO {

    private Long id;

    private Boolean choix;

    private LocalDateTime dateVote;

    private Long utilisateurId;

    private Long voteId;
}