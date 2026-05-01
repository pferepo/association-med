package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.VoteResponseDTO;
import tn.association.med.entities.User;
import tn.association.med.service.UserService;
import tn.association.med.service.VoteService;

import java.util.List;

@Tag(
        name = "Votes",
        description = "Gestion du système de vote pour les activités"
)
@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;

    @Operation(
            summary = "Participer à un vote",
            description = "Permet à un utilisateur connecté de voter (oui/non) pour une activité."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote enregistré"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "400", description = "Utilisateur a déjà voté")
    })
    @PostMapping("/{voteId}/participer")
    public ResponseEntity<String> participer(
            @Parameter(description = "ID du vote", example = "1")
            @PathVariable Long voteId,

            Authentication authentication,

            @Parameter(description = "Choix du vote (true = oui, false = non)", example = "true")
            @RequestParam Boolean choix
    ) {

        User utilisateur = userService.getUserByEmail(authentication.getName());

        voteService.createVote(voteId, choix, utilisateur);

        return ResponseEntity.ok("Vote enregistré");
    }

    @Operation(
            summary = "Détails d'un vote",
            description = "Retourne les informations d'un vote spécifique."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote trouvé"),
            @ApiResponse(responseCode = "404", description = "Vote introuvable")
    })
    @GetMapping("/{id}")
    public VoteResponseDTO getVoteById(
            @Parameter(description = "ID du vote", example = "1")
            @PathVariable Long id) {

        return voteService.getVoteById(id);
    }

    @Operation(
            summary = "Liste des votes",
            description = "Retourne la liste de tous les votes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée")
    })
    @GetMapping
    public List<VoteResponseDTO> getAllVotes() {
        return voteService.getAllVotes();
    }

    @Operation(
            summary = "Fermer un vote",
            description = "Permet de fermer un vote afin qu'il ne soit plus possible de voter."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote fermé"),
            @ApiResponse(responseCode = "404", description = "Vote introuvable")
    })
    @PutMapping("/{id}/close")
    public VoteResponseDTO closeVote(
            @Parameter(description = "ID du vote", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Résultat du vote (true = validé, false = refusé)", example = "true")
            @RequestParam boolean approve
    ) {
        return voteService.closeVote(id, approve);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote supprimé"),
            @ApiResponse(responseCode = "404", description = "Vote introuvable")
    })
    @DeleteMapping("/{id}")
    public void deleteVote(
            @Parameter(description = "ID du vote", example = "1")
            @PathVariable Long id
    ) {
        voteService.deleteVote(id);
    }
}