package tn.association.med.dto;

import lombok.Data;
import tn.association.med.enums.Role;

@Data
public class UserRequestDTO {

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Role role;
}