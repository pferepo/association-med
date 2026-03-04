package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.*;
import tn.association.med.entities.Activite;
import tn.association.med.mapper.ActiviteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.service.ActiviteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiviteServiceImpl implements ActiviteService {

    private final ActiviteRepository repository;
    private final ActiviteMapper mapper;

    @Override
    public ActiviteResponseDTO create(ActiviteRequestDTO dto) {
        Activite activite = mapper.toEntity(dto);
        Activite saved = repository.save(activite);
        return mapper.toDto(saved);
    }

    @Override
    public List<ActiviteResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ActiviteResponseDTO getById(Long id) {
        Activite activite = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activite not found"));
        return mapper.toDto(activite);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}