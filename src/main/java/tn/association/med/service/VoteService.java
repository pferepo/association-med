package tn.association.med.service;

import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.User;

import java.util.List;

public interface VoteService {

    void createVote(Long voteId, Boolean choix, User utilisateur);
    
    VoteResponseDTO getVoteById(Long id);

    List<VoteResponseDTO> getAllVotes();

    VoteResponseDTO closeVote(Long id);
}