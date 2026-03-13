package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.ParticipationVoteRequestDTO;
import tn.association.med.dto.ParticipationVoteResponseDTO;
import tn.association.med.service.ParticipationVoteService;

import java.util.List;

@RestController
@RequestMapping("/api/participation-votes")
@RequiredArgsConstructor
public class ParticipationVoteController {

    private final ParticipationVoteService participationVoteService;

    @PostMapping
    public ParticipationVoteResponseDTO voter(@RequestBody ParticipationVoteRequestDTO dto) {
        return participationVoteService.voter(dto);
    }

    @GetMapping("/vote/{voteId}")
    public List<ParticipationVoteResponseDTO> getVotesByVote(@PathVariable Long voteId) {
        return participationVoteService.getVotesByVote(voteId);
    }
}