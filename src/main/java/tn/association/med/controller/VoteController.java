package tn.association.med.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.VoteRequestDTO;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public VoteResponseDTO createVote(@RequestBody VoteRequestDTO dto) {
        return voteService.createVote(dto);
    }

    @GetMapping("/{id}")
    public VoteResponseDTO getVoteById(@PathVariable Long id) {
        return voteService.getVoteById(id);
    }

    @GetMapping
    public List<VoteResponseDTO> getAllVotes() {
        return voteService.getAllVotes();
    }

    @PutMapping("/{id}/close")
    public VoteResponseDTO closeVote(@PathVariable Long id) {
        return voteService.closeVote(id);
    }
}