package tn.association.med.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteRequestDTO {

    private Long activiteId;

    private String statut;
}