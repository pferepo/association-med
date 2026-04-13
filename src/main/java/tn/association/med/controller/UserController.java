package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import tn.association.med.dto.EmailRequest;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.User;
import tn.association.med.service.UserService;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Utilisateurs",
        description = "Gestion des utilisateurs de l'application"
)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // --- Créer un utilisateur ---
    @Operation(summary = "Créer un utilisateur", description = "Permet d'enregistrer un nouvel utilisateur dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/register")
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO updateUser(@PathVariable Long id,
                                      @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    // --- Activer/Désactiver utilisateur ---
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/toggle-active")
    public UserResponseDTO toggleActive(@PathVariable Long id) {
        return userService.toggleUserActive(id);
    }

    // --- Liste de tous les utilisateurs ---
    @Operation(summary = "Liste de tous les utilisateurs", description = "Retourne tous les utilisateurs (réservé à l'ADMIN).")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    // --- Liste de tous les emails ---
    @Operation(summary = "Liste de tous les emails", description = "Retourne les emails de tous les utilisateurs (réservé à l'ADMIN).")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/emails")
    public List<String> getEmails() {
        return userService.getAllEmailUsers();
    }

    // --- Trouver un utilisateur par ID ---
    @Operation(summary = "Trouver un utilisateur par ID", description = "Retourne un utilisateur spécifique par son identifiant.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDTO getById(
            @Parameter(description = "ID de l'utilisateur", example = "1")
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    // --- Trouver un utilisateur par email ---
    @Operation(summary = "Trouver un utilisateur par email", description = "Retourne un utilisateur à partir de son adresse email.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-email")
    public User getUserByEmail(
            @Parameter(description = "Email de l'utilisateur", example = "admin@mail.com")
            @RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    // --- Supprimer un utilisateur ---
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son identifiant.")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID de l'utilisateur", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
    }

    // --- Utilisateur connecté ---
    @Operation(summary = "Utilisateur connecté", description = "Retourne les informations de l'utilisateur actuellement authentifié.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public User currentUser(Authentication authentication) {
        return userService.getUserByEmail(authentication.getName());
    }

    // --- Reset password ---
    @PostMapping("/send-reset-code")
    public void sendResetCode(@RequestBody EmailRequest request) {
        userService.sendResetCode(request.getEmail());
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody Map<String, String> req) {
        userService.resetPassword(
                req.get("email"),
                req.get("code"),
                req.get("newPassword")
        );
    }


    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        return userService.uploadUserImage(id, file);
    }
}