package tn.association.med.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.association.med.dto.ParticipationRequestDTO;
import tn.association.med.dto.ParticipationResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Participation;

@Component
@RequiredArgsConstructor
public class ParticipationMapper {

    public Participation toEntity(ParticipationRequestDTO dto, Activite activite) {

        return Participation.builder()
                .nomParticipant(dto.getNomParticipant())
                .emailParticipant(dto.getEmailParticipant())
                .activite(activite)
                .build();
    }

    public ParticipationResponseDTO toDto(Participation participation) {

        return ParticipationResponseDTO.builder()
                .id(participation.getId())
                .nomParticipant(participation.getNomParticipant())
                .emailParticipant(participation.getEmailParticipant())
                .activiteId(participation.getActivite().getId())
                .activiteTitre(participation.getActivite().getTitre())
                .build();
    }
}