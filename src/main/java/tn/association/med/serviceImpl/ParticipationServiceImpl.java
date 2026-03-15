package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.ParticipationRequestDTO;
import tn.association.med.dto.ParticipationResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Participation;
import tn.association.med.mapper.ParticipationMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.repository.ParticipationRepository;
import tn.association.med.service.ParticipationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ActiviteRepository activiteRepository;
    private final ParticipationMapper participationMapper;

    @Override
    public ParticipationResponseDTO create(ParticipationRequestDTO dto) {

        Activite activite = activiteRepository.findById(dto.getActiviteId())
                .orElseThrow(() -> new RuntimeException("Activite non trouvée"));

        Participation participation = participationMapper.toEntity(dto, activite);

        Participation saved = participationRepository.save(participation);

        return participationMapper.toDto(saved);
    }

    @Override
    public List<ParticipationResponseDTO> getAll() {
        return participationRepository.findAll()
                .stream()
                .map(participationMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        participationRepository.deleteById(id);
    }
}