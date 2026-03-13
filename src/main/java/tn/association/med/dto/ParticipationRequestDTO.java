package tn.association.med.dto;

import lombok.Data;

@Data
public class ParticipationRequestDTO {

    private String nomParticipant;
    private String emailParticipant;
    private Long activiteId;

}