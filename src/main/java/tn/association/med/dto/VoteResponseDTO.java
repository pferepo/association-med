package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VoteResponseDTO {

    private Long id;

    private LocalDateTime dateLimite;

    private String statut;

    private Long activiteId;
}