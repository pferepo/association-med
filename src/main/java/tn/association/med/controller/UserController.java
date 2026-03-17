package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.User;
import tn.association.med.service.UserService;

import java.util.List;

@Tag(
        name = "Utilisateurs",
        description = "Gestion des utilisateurs de l'application"
)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Créer un utilisateur",
            description = "Permet d'enregistrer un nouvel utilisateur dans le système."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @Operation(
            summary = "Liste des utilisateurs",
            description = "Retourne tous les utilisateurs (réservé à l'ADMIN)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Trouver un utilisateur par ID",
            description = "Retourne un utilisateur spécifique par son identifiant."
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getById(
            @Parameter(description = "ID de l'utilisateur", example = "1")
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @Operation(
            summary = "Trouver un utilisateur par email",
            description = "Retourne un utilisateur à partir de son adresse email."
    )
    @GetMapping("/by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserByEmail(
            @Parameter(description = "Email de l'utilisateur", example = "admin@mail.com")
            @RequestParam String email) {

        return userService.getUserByEmail(email);
    }

    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur par son identifiant."
    )
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID de l'utilisateur", example = "1")
            @PathVariable Long id) {

        userService.deleteUser(id);
    }

    @Operation(
            summary = "Utilisateur connecté",
            description = "Retourne les informations de l'utilisateur actuellement authentifié."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur récupéré"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public User currentUser(Authentication authentication) {

        return userService.getUserByEmail(authentication.getName());
    }
}