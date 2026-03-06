package tn.association.med.service;

import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;

import java.util.List;

public interface ActiviteService {

    ActiviteResponseDTO create(ActiviteRequestDTO dto);

    List<ActiviteResponseDTO> getAll();

    ActiviteResponseDTO getById(Long id);

    ActiviteResponseDTO updateActivite(Long id, ActiviteRequestDTO dto);

    void delete(Long id);
}