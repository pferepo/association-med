package tn.association.med.mapper;

import org.springframework.stereotype.Component;
import tn.association.med.dto.UserRequestDTO;
import tn.association.med.dto.UserResponseDTO;
import tn.association.med.entities.User;
import tn.association.med.enums.Genre;

@Component
public class UserMapper {

    // DTO → Entity 
    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .genre(Genre.valueOf(dto.getGenre()))
                .role(dto.getRole())
                .build();
    }

    // Entity → DTO 
    public UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .genre(String.valueOf(user.getGenre()))
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}