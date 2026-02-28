package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.User;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}