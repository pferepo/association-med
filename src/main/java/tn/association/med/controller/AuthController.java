package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.AuthRequestDTO;
import tn.association.med.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO request) {

        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }


}