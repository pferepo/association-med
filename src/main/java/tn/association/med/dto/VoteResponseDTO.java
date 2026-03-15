package tn.association.med.dto;

import lombok.Builder;
import lombok.Data;
import tn.association.med.enums.VoteStatus;

import java.util.Date;

@Data
@Builder
public class VoteResponseDTO {

    private Long id;

    private Date dateLimite;

    private VoteStatus statut;

    private Long activiteId;
}