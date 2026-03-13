package tn.association.med.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.ParticipationVoteRequestDTO;
import tn.association.med.dto.ParticipationVoteResponseDTO;
import tn.association.med.entities.ParticipationVote;
import tn.association.med.entities.User;
import tn.association.med.entities.Vote;
import tn.association.med.mapper.ParticipationVoteMapper;
import tn.association.med.repository.ParticipationVoteRepository;
import tn.association.med.repository.UserRepository;
import tn.association.med.repository.VoteRepository;
import tn.association.med.service.ParticipationVoteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationVoteServiceImpl implements ParticipationVoteService {

    private final ParticipationVoteRepository participationVoteRepository;
    private final UserRepository utilisateurRepository;
    private final VoteRepository voteRepository;
    private final ParticipationVoteMapper mapper;

    @Override
    public ParticipationVoteResponseDTO voter(ParticipationVoteRequestDTO dto) {

        User user = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vote vote = voteRepository.findById(dto.getVoteId())
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        ParticipationVote participation = ParticipationVote.builder()
                .utilisateur(user)
                .vote(vote)
                .choix(dto.getChoix())
                .dateVote(LocalDateTime.now())
                .build();

        return mapper.toDto(participationVoteRepository.save(participation));
    }

    @Override
    public List<ParticipationVoteResponseDTO> getVotesByVote(Long voteId) {

        return participationVoteRepository.findAll()
                .stream()
                .filter(v -> v.getVote().getId().equals(voteId))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}