package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.service.ActiviteService;

import java.util.List;

@Tag(
        name = "Activités",
        description = "API de gestion des activités de l'association"
)
@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActiviteController {

    private final ActiviteService activiteService;

    @Operation(
            summary = "Créer une activité",
            description = "Permet de créer une nouvelle activité. Accessible aux rôles ADMIN et MEMBRE."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activité créée avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (rôle insuffisant)"),
            @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public ActiviteResponseDTO create(@RequestBody ActiviteRequestDTO dto) {
        return activiteService.create(dto);
    }

    @Operation(
            summary = "Liste de toutes les activités",
            description = "Retourne toutes les activités disponibles. Accessible aux rôles ADMIN et MEMBRE."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public List<ActiviteResponseDTO> getAll() {
        return activiteService.getAll();
    }

    @Operation(
            summary = "Activités visibles par les invités",
            description = "Retourne les activités accessibles au rôle INVITE. Accessible aux rôles ADMIN, MEMBRE et INVITE."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping("/invite")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE','INVITE')")
    public List<ActiviteResponseDTO> getActivitiesForInvite() {
        return activiteService.getActivitiesInvite();
    }

    @Operation(
            summary = "Obtenir une activité par ID",
            description = "Retourne les détails d'une activité spécifique."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activité trouvée"),
            @ApiResponse(responseCode = "404", description = "Activité introuvable")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public ActiviteResponseDTO getById(
            @Parameter(description = "ID de l'activité", example = "1")
            @PathVariable Long id) {

        return activiteService.getById(id);
    }

    @Operation(
            summary = "Modifier une activité",
            description = "Met à jour les informations d'une activité existante. Accessible uniquement au rôle ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activité mise à jour"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "Activité introuvable")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActiviteResponseDTO> update(
            @Parameter(description = "ID de l'activité à modifier", example = "1")
            @PathVariable Long id,
            @RequestBody ActiviteRequestDTO dto) throws Exception {

        ActiviteResponseDTO updated = activiteService.updateActivite(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Supprimer une activité",
            description = "Supprime une activité par son identifiant. Accessible uniquement au rôle ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activité supprimée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "Activité introuvable")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(
            @Parameter(description = "ID de l'activité à supprimer", example = "1")
            @PathVariable Long id) {

        activiteService.delete(id);
    }
}