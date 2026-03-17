package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Vote;
import tn.association.med.enums.StatutProposition;
import tn.association.med.enums.TypeAction;
import tn.association.med.mapper.ActiviteMapper;
import tn.association.med.mapper.VoteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.repository.VoteRepository;
import tn.association.med.service.ActiviteService;
import tn.association.med.service.HistoriqueService;
import tn.association.med.serviceImpl.notification.EmailNotifsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiviteServiceImpl implements ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final ActiviteMapper activiteMapper;
    private final EmailNotifsService emailNotifsService;
    private final HistoriqueService historiqueService;
    private final VoteRepository voteRepository;

    @Override
    public ActiviteResponseDTO create(ActiviteRequestDTO dto) {
        // Mapper le DTO vers l'entité
        Activite activite = activiteMapper.toEntity(dto);

        //  Sauvegarder dans la base
        Activite saved = activiteRepository.save(activite);

        //  Envoyer un email à tous les membres
        for (String email : saved.getMembres()) {
            emailNotifsService.envoyerEmail(
                email,               // email du membre
                saved.getTitre(),    // sujet = titre de l'activité
                saved.getDescription() // corps = description
            );
        }
     // Historique
        historiqueService.save(TypeAction.ACTIVITE,"ACTIVITE", saved.getId(), saved.getDescription(), 1L);
        // TODO corriger Id User dans Historique

        //  Retourner le DTO de réponse
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
    public ActiviteResponseDTO updateActivite(Long id, ActiviteRequestDTO dto) throws Exception {

        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activite non trouvée"));

        activite.setTitre(dto.getTitre());
        activite.setDescription(dto.getDescription());
        activite.setType(dto.getType());

        activite.setStatut((dto.getStatut()));
        activite.setStatutProposition((dto.getStatutProposition()));

        Activite updated = activiteRepository.save(activite);
        if(dto.getStatutProposition().toString().equals(null)) {
        	throw new Exception("statut proposition est null !");
        }

        if (activite.getStatutProposition() == StatutProposition.POUR_VOTE) {

            Vote vote = Vote.builder()
                    .description("Vote pour l'activité: " + activite.getTitre())
                    .activite(activite)
                    .build();

            voteRepository.save(vote);
            if(!(activite.getMembres()==null)) {
            //  Envoyer un email à tous les membres
                for (String email : activite.getMembres()) {
                    emailNotifsService.envoyerEmail(
                        email,               // email du membre
                        activite.getTitre(),    // sujet = titre de l'activité
                        activite.getDescription() // corps = description
                    );
                }
            }
        
         // Historique
            historiqueService.save(
            	    TypeAction.VOTE,
            	    "VOTE ouvert pour Activité ID " + vote.getActivite().getId(),
            	    vote.getId(),
            	    vote.getDescription(),
            	    1L
            	);            
        }
        return activiteMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        activiteRepository.deleteById(id);
    }


	@Override
	public List<ActiviteResponseDTO> getActivitiesInvite() {
		
		return activiteRepository.getActivitiesInvite()
                .stream()
                .map(activiteMapper::toDto)
                .toList();
	}
}