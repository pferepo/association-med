package tn.association.med.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.User;
import tn.association.med.service.UserService;
import tn.association.med.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;

    @PostMapping("/{voteId}/participer")
    public ResponseEntity<String> participer(
            @PathVariable Long voteId,
            @RequestParam Long userId, // TODO à modifier après spring security par extraction de
            // Authentication authentication puis ci dessou :
            // User utilisateur = userService.getUserEntityByEmail(authentication.getName());
            @RequestParam Boolean choix
    ) {

        User utilisateur = userService.getUserEntityById(userId);

        voteService.createVote(voteId, choix, utilisateur);

        return ResponseEntity.ok("Vote enregistré");
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