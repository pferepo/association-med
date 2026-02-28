package tn.association.med.dto;

import lombok.*;
import tn.association.med.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private Boolean active;
    private LocalDateTime createdAt;
}