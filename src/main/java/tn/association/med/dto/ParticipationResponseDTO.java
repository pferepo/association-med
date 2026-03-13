package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipationResponseDTO {

    private Long id;
    private String nomParticipant;
    private String emailParticipant;

    private Long activiteId;
    private String activiteTitre;

}