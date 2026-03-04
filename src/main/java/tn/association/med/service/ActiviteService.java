package tn.association.med.service;

import tn.association.med.dto.*;

import java.util.List;

public interface ActiviteService {

    ActiviteResponseDTO create(ActiviteRequestDTO dto);

    List<ActiviteResponseDTO> getAll();

    ActiviteResponseDTO getById(Long id);

    void delete(Long id);
}
