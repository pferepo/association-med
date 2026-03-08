package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.VoteRequestDTO;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Vote;
import tn.association.med.mapper.VoteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.repository.VoteRepository;
import tn.association.med.service.VoteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ActiviteRepository activiteRepository;
    private final VoteMapper mapper;

    @Override
    public VoteResponseDTO createVote(VoteRequestDTO dto) {

        Activite activite = activiteRepository.findById(dto.getActiviteId())
                .orElseThrow(() -> new RuntimeException("Activite not found"));

        Vote vote = Vote.builder()
                .activite(activite)
                .statut(dto.getStatut())
                .build();

        return mapper.toDto(voteRepository.save(vote));
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

        vote.setStatut("FERME");

        return mapper.toDto(voteRepository.save(vote));
    }
}