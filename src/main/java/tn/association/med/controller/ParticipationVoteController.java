package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.ParticipationVoteRequestDTO;
import tn.association.med.dto.ParticipationVoteResponseDTO;
import tn.association.med.service.ParticipationVoteService;

import java.util.List;

@Tag(
        name = "Votes",
        description = "Gestion des participations au vote pour les activités"
)
@RestController
@RequestMapping("/api/participation-votes")
@RequiredArgsConstructor
public class ParticipationVoteController {

    private final ParticipationVoteService participationVoteService;

    @Operation(
            summary = "Participer à un vote",
            description = "Permet à un utilisateur de voter pour une activité."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote enregistré avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "409", description = "Utilisateur a déjà voté")
    })
    @PostMapping
    public ParticipationVoteResponseDTO voter(
            @RequestBody ParticipationVoteRequestDTO dto) {

        return participationVoteService.voter(dto);
    }

    @Operation(
            summary = "Liste des votes d'un vote spécifique",
            description = "Retourne toutes les participations associées à un vote donné."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des votes récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Vote introuvable")
    })
    @GetMapping("/vote/{voteId}")
    public List<ParticipationVoteResponseDTO> getVotesByVote(
            @Parameter(description = "ID du vote", example = "1")
            @PathVariable Long voteId) {

        return participationVoteService.getVotesByVote(voteId);
    }
}