package tn.association.med.dto;

import lombok.Data;

@Data
public class ParticipationVoteRequestDTO {

    private Long utilisateurId;

    private Long voteId;

    private Boolean choix;
}