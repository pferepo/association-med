package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.entities.Historique;
import tn.association.med.enums.TypeAction;
import tn.association.med.repository.HisotriqueRepository;
import tn.association.med.service.HistoriqueService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService{

    private final HisotriqueRepository hisotriqueRepository;

    public void save(TypeAction action, String entityName, Long entityId, String description, Long idUser){

        Historique historique = Historique.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .description(description)
                .idUser(idUser)
                .build();

        hisotriqueRepository.save(historique); 
    }

    @Override
    public List<Historique> findAll() {
        return hisotriqueRepository.findAllByOrderByDateActionDesc(); // pour afficher du plus récent au plus ancien
    }

}