package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.ParticipationVote;
import tn.association.med.entities.User;
import tn.association.med.entities.Vote;
import tn.association.med.enums.StatutActivite;
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

    // -------------------- Participer à un vote --------------------
    @Override
    @Transactional
    public void createVote(Long voteId, Boolean choix, User utilisateur) {

        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("Vote introuvable"));

        boolean dejaVote = participationVoteRepository
                .existsByUtilisateurIdAndVoteId(utilisateur.getId(), voteId);

        if (dejaVote) {
            throw new RuntimeException("Vous avez déjà voté pour ce vote");
        }

        ParticipationVote participation = ParticipationVote.builder()
                .vote(vote)
                .utilisateur(utilisateur)
                .choix(choix)
                .dateVote(LocalDateTime.now())
                .build();

        participationVoteRepository.save(participation);
    }

    // -------------------- Récupérer un vote par ID --------------------
    @Override
    public VoteResponseDTO getVoteById(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote introuvable"));

        return mapper.toDto(vote);
    }

    // -------------------- Récupérer tous les votes --------------------
    @Override
    public List<VoteResponseDTO> getAllVotes() {
        return voteRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // -------------------- Fermer un vote --------------------
    @Override
    @Transactional
    public VoteResponseDTO closeVote(Long id, boolean approuve) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote introuvable"));

        vote.setStatut(VoteStatus.FERME);
        Activite activite = vote.getActivite();
        if (approuve) {
            activite.setStatut(StatutActivite.VALIDEE);
            activite.setDateValidation(LocalDateTime.now());
        } else {
            activite.setStatut(StatutActivite.REFUSEE);
        }


        Vote saved = voteRepository.save(vote);
        return mapper.toDto(saved);
    }

    @Override
    public void deleteVote(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote introuvable avec l'id : " + id));

        voteRepository.delete(vote);
    }

}