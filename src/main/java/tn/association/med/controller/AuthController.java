package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import tn.association.med.dto.AuthRequestDTO;
import tn.association.med.service.AuthService;

@Tag(
        name = "Authentification",
        description = "API de gestion de l'authentification et génération du JWT"
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur avec email et mot de passe et retourne un token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie, JWT retourné"),
            @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect"),
            @ApiResponse(responseCode = "403", description = "Accès interdit")
    })
    @PostMapping("/login")
    public String login(
            @RequestBody(description = "Informations de connexion utilisateur", required = true)
            @org.springframework.web.bind.annotation.RequestBody AuthRequestDTO request
    ) {

        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}