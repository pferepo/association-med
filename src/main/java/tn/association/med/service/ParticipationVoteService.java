package tn.association.med.service;

import tn.association.med.dto.ParticipationVoteRequestDTO;
import tn.association.med.dto.ParticipationVoteResponseDTO;

import java.util.List;

public interface ParticipationVoteService {

    ParticipationVoteResponseDTO voter(ParticipationVoteRequestDTO dto);

    List<ParticipationVoteResponseDTO> getVotesByVote(Long voteId);
}