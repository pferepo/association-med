package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.ParticipationVote;
import tn.association.med.entities.User;
import tn.association.med.entities.Vote;
import tn.association.med.enums.VoteStatus;
import tn.association.med.mapper.VoteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.repository.ParticipationVoteRepository;
import tn.association.med.repository.VoteRepository;
import tn.association.med.service.VoteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ActiviteRepository activiteRepository;
    private final ParticipationVoteRepository participationVoteRepository;
    private final VoteMapper mapper;

    @Override
    public void createVote(Long voteId, Boolean choix, User utilisateur) {

        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("Vote introuvable"));

        boolean dejaVote = participationVoteRepository
                .existsByUtilisateurIdAndVoteId(utilisateur.getId(), voteId);

        if (dejaVote) {
            throw new RuntimeException("Vous avez déjà voté pour cette activité");
        }

        ParticipationVote participation = ParticipationVote.builder()
                .vote(vote)
                .utilisateur(utilisateur)
                .choix(choix)
                .dateVote(LocalDateTime.now())
                .build();

        participationVoteRepository.save(participation);
    }

    @Override
    public VoteResponseDTO getVoteById(Long id) {

        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        return mapper.toDto(vote);
    }

    @Override
    public List<VoteResponseDTO> getAllVotes() {

        return voteRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VoteResponseDTO closeVote(Long id) {

        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        vote.setStatut(VoteStatus.FERME);

        return mapper.toDto(voteRepository.save(vote));
    }
}