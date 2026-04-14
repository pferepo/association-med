package tn.association.med.dto;

import lombok.*;
import tn.association.med.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String genre;
    private Role role;
    private Boolean active;
    private String tel;
    private String grade;
    private String cin;
}