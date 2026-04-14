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
                .genre(dto.getGenre() != null ? Genre.valueOf(dto.getGenre()) : null)
                .active(dto.getActive() != null ? dto.getActive() : false)
                .role(dto.getRole())
                .tel(dto.getTel())
                .grade(dto.getGrade())
                .cin(dto.getCin())

                .build();
    }

    // Entity → DTO
    public UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .genre(user.getGenre() != null ? user.getGenre().name() : null)
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .imageUrl(user.getImageUrl())
                .tel(user.getTel())
                .grade(user.getGrade())
                .cin(user.getCin())

                .build();
    }
}