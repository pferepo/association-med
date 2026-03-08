package tn.association.med.service;

import tn.association.med.dto.VoteRequestDTO;
import tn.association.med.dto.VoteResponseDTO;

import java.util.List;

public interface VoteService {

    VoteResponseDTO createVote(VoteRequestDTO dto);

    VoteResponseDTO getVoteById(Long id);

    List<VoteResponseDTO> getAllVotes();

    VoteResponseDTO closeVote(Long id);
}