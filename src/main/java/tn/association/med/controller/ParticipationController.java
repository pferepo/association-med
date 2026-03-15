package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.ParticipationRequestDTO;
import tn.association.med.dto.ParticipationResponseDTO;
import tn.association.med.service.ParticipationService;

import java.util.List;

@Tag(
        name = "Participations",
        description = "Gestion des participations des membres aux activités"
)
@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @Operation(
            summary = "Créer une participation",
            description = "Permet à un utilisateur de participer à une activité."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participation créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PostMapping
    public ParticipationResponseDTO create(
            @RequestBody ParticipationRequestDTO dto) {

        return participationService.create(dto);
    }

    @Operation(
            summary = "Liste des participations",
            description = "Retourne la liste de toutes les participations."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public List<ParticipationResponseDTO> getAll() {
        return participationService.getAll();
    }

    @Operation(
            summary = "Supprimer une participation",
            description = "Supprime une participation par son identifiant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participation supprimée"),
            @ApiResponse(responseCode = "404", description = "Participation introuvable"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID de la participation à supprimer", example = "1")
            @PathVariable Long id) {

        participationService.delete(id);
    }
}