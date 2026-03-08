package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.enums.StatutActivite;
import tn.association.med.mapper.ActiviteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.service.ActiviteService;
import tn.association.med.serviceImpl.notification.EmailNotifsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiviteServiceImpl implements ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final ActiviteMapper activiteMapper;
    private final EmailNotifsService emailNotifsService;

    @Override
    public ActiviteResponseDTO create(ActiviteRequestDTO dto) {
        Activite activite = activiteMapper.toEntity(dto);
        Activite saved = activiteRepository.save(activite);

        emailNotifsService.envoyerEmail("plusmarwan@gmail.com","reunion notif","test");

        return activiteMapper.toDto(saved);
    }
    

    @Override
    public List<ActiviteResponseDTO> getAll() {
        return activiteRepository.findAll()
                .stream()
                .map(activiteMapper::toDto)
                .toList();
    }

    @Override
    public ActiviteResponseDTO getById(Long id) {
        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activite not found"));

        return activiteMapper.toDto(activite);
    }

    @Override
    public ActiviteResponseDTO updateActivite(Long id, ActiviteRequestDTO dto) {

        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activite non trouvée"));

        activite.setTitre(dto.getTitre());
        activite.setDescription(dto.getDescription());
        activite.setType(dto.getType());

        activite.setStatut((dto.getStatut()));
        activite.setStatutProposition((dto.getStatutProposition()));

        Activite updated = activiteRepository.save(activite);

        return activiteMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        activiteRepository.deleteById(id);
    }
}