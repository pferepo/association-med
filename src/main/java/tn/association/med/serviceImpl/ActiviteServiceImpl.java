package tn.association.med.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.entities.Activite;
import tn.association.med.entities.Createur;
import tn.association.med.entities.User;
import tn.association.med.entities.Vote;
import tn.association.med.enums.StatutProposition;
import tn.association.med.enums.TypeAction;
import tn.association.med.mapper.ActiviteMapper;
import tn.association.med.repository.ActiviteRepository;
import tn.association.med.repository.UserRepository;
import tn.association.med.repository.VoteRepository;
import tn.association.med.service.ActiviteService;
import tn.association.med.service.HistoriqueService;
import tn.association.med.serviceImpl.notification.EmailNotifsService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActiviteServiceImpl implements ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final ActiviteMapper activiteMapper;
    private final EmailNotifsService emailNotifsService;
    private final HistoriqueService historiqueService;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository; // pour récupérer l'utilisateur si principal = String

    // Méthode helper pour récupérer l'utilisateur connecté

    private User getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return user;
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            String email = ud.getUsername(); // ici username = email
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé pour email : " + email));
        } else if (principal instanceof String email) {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé pour email : " + email));
        }

        throw new RuntimeException("Type de principal inconnu : " + principal.getClass());
    }

    @Override
    public ActiviteResponseDTO create(ActiviteRequestDTO dto) {
        User connectedUser = getConnectedUser();
        Createur createur = new Createur(connectedUser.getNom(), connectedUser.getPrenom());

        // Mapper DTO -> Entité
        Activite activite = activiteMapper.toEntity(dto);
        activite.setCreateur(createur);

        // Sauvegarder l'activité
        Activite saved = activiteRepository.save(activite);

        // Envoyer les emails aux membres
        if (saved.getMembres() != null) {
            for (String email : saved.getMembres()) {
                emailNotifsService.envoyerEmail(email, saved.getTitre(), saved.getDescription());
            }
        }

        // Historique création activité
        historiqueService.save(TypeAction.ACTIVITE, "ACTIVITE", saved.getId(), saved.getDescription(), connectedUser.getId());

        // Création vote si statut = POUR_VOTE
        if (saved.getStatutProposition() == StatutProposition.POUR_VOTE) {
            Vote vote = Vote.builder()
                    .description("Vote pour l'activité: " + saved.getTitre())
                    .activite(saved)
                    .build();
            voteRepository.save(vote);

            // Historique vote
            historiqueService.save(TypeAction.VOTE,
                    "VOTE ouvert pour Activité ID " + vote.getActivite().getId(),
                    vote.getId(),
                    vote.getDescription(),
                    connectedUser.getId());
        }

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
                .orElseThrow(() -> new RuntimeException("Activite non trouvée"));
        return activiteMapper.toDto(activite);
    }

    @Override
    public ActiviteResponseDTO updateActivite(Long id, ActiviteRequestDTO dto) throws Exception {
        User connectedUser = getConnectedUser();

        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        // Mise à jour des champs
        activite.setTitre(dto.getTitre());
        activite.setDescription(dto.getDescription());
        activite.setType(dto.getType());
        activite.setStatut(dto.getStatut());
        activite.setMembres(dto.getMembre());
        activite.setStatutProposition(dto.getStatutProposition());

        Activite updated = activiteRepository.save(activite);

        // Vérification et création/modification du vote
        if (activite.getStatutProposition() == StatutProposition.POUR_VOTE) {
            Optional<Vote> existingVote = voteRepository.findByActivite(activite);

            if (existingVote.isPresent()) {
                Vote vote = existingVote.get();
                vote.setDescription("Vote pour l'activité: " + activite.getTitre());
                voteRepository.save(vote);
                historiqueService.save(TypeAction.VOTE,
                        "VOTE modifié pour Activité ID " + vote.getActivite().getId(),
                        vote.getId(),
                        vote.getDescription(),
                        connectedUser.getId());
            } else {
                Vote vote = Vote.builder()
                        .description("Vote pour l'activité: " + activite.getTitre())
                        .activite(activite)
                        .build();
                voteRepository.save(vote);
                historiqueService.save(TypeAction.VOTE,
                        "VOTE créé pour Activité ID " + vote.getActivite().getId(),
                        vote.getId(),
                        vote.getDescription(),
                        connectedUser.getId());
            }
        }

        // Envoyer les emails aux membres
        if (activite.getMembres() != null) {
            for (String email : activite.getMembres()) {
                emailNotifsService.envoyerEmail(email, activite.getTitre(), activite.getDescription());
            }
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