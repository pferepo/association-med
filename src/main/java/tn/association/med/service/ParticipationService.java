package tn.association.med.service;

import tn.association.med.dto.ParticipationRequestDTO;
import tn.association.med.dto.ParticipationResponseDTO;

import java.util.List;

public interface ParticipationService {

    ParticipationResponseDTO create(ParticipationRequestDTO dto);

    List<ParticipationResponseDTO> getAll();

    void delete(Long id);
}