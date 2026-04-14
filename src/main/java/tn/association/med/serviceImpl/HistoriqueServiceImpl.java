package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Historique;
import tn.association.med.entities.User;
import tn.association.med.enums.HistoriqueStatus;
import tn.association.med.enums.TypeAction;
import tn.association.med.repository.HisotriqueRepository;
import tn.association.med.service.HistoriqueService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService{

    private final HisotriqueRepository hisotriqueRepository;

    @Override
    public void saveHistorique(
            TypeAction type,
            String action,
            Long referenceId,
            Activite activite,
            User user
    ) {
        String message = String.format(
                """
                📌 ACTION: %s
                👤 UTILISATEUR: %s %s (ID: %s)
    
                🆔 ID: %s
                📍 TITRE: %s
                📝 DESCRIPTION: %s
                📂 TYPE: %s
                📊 STATUT: %s
                🗳️ PROPOSITION: %s
                """,
                action,
                user.getPrenom(),
                user.getNom(),
                user.getId(),

                activite != null ? activite.getId() : referenceId,
                activite != null ? activite.getTitre() : "N/A",
                activite != null ? activite.getDescription() : "N/A",
                activite != null ? activite.getType() : "N/A",
                activite != null ? activite.getStatut() : "N/A",
                activite != null ? activite.getStatutProposition() : "N/A"
        );

        Historique historique = Historique.builder()
                .action(type)
                .entityName(action)
                .entityId(referenceId)
                .description(message)
                .idUser(user.getId())
                .status(HistoriqueStatus.SUCCESS)
                .build();

        hisotriqueRepository.save(historique);
    }

    @Override
    public List<Historique> findAll() {
        return hisotriqueRepository.findAllByOrderByDateActionDesc(); // pour afficher du plus récent au plus ancien
    }

}