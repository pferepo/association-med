package tn.association.med.dto;

import lombok.*;
import tn.association.med.enums.VoteStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResponseDTO {

    private Long id;

    private String description;

    private Date dateLimite;

    private VoteStatus statut;

    private Long activiteId;

    private LocalDateTime dateCreation;
}