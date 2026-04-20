package tn.association.med.serviceImpl;

import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;

    private User getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return user;
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            String email = ud.getUsername();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + email));
        } else if (principal instanceof String email) {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + email));
        }

        throw new RuntimeException("Type principal inconnu : " + principal.getClass());
    }

    // =========================
    // CREATE ACTIVITE
    // =========================
    @Override
    public ActiviteResponseDTO create(ActiviteRequestDTO dto) {

        User connectedUser = getConnectedUser();
        Createur createur = new Createur(connectedUser.getNom(), connectedUser.getPrenom());

        Activite activite = activiteMapper.toEntity(dto);
        activite.setCreateur(createur);

        Activite saved = activiteRepository.save(activite);

        if (saved.getMembres() != null && !saved.getMembres().isEmpty()) {
            saved.getMembres().stream()
                    .distinct()
                    .forEach(email ->
                            emailNotifsService.envoyerEmail(
                                    email,
                                    saved.getTitre(),
                                    saved.getDescription()
                            )
                    );
        }

        // HISTORIQUE
        historiqueService.saveHistorique(
                TypeAction.ACTIVITE,
                "ACTIVITE_CREATED",
                saved.getId(),
                saved,
                connectedUser
        );

        if (saved.getStatutProposition() == StatutProposition.POUR_VOTE) {

            Vote vote = Vote.builder()
                    .description("Vote pour l'activité: " + saved.getTitre())
                    .activite(saved)
                    .dateLimite(dto.getDateLimiteVote())
                    .build();

            Vote savedVote = voteRepository.save(vote);

            //  HISTORIQUE VOTE
            historiqueService.saveHistorique(
                    TypeAction.VOTE,
                    "VOTE_CREATED",
                    savedVote.getId(),
                    saved,
                    connectedUser
            );
        }

        return activiteMapper.toDto(saved);
    }

    // =========================
    // GET ALL
    // =========================
    @Override
    public List<ActiviteResponseDTO> getAll() {
        return activiteRepository.findAll()
                .stream()
                .map(activiteMapper::toDto)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public ActiviteResponseDTO getById(Long id) {
        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        return activiteMapper.toDto(activite);
    }

    // =========================
    // UPDATE ACTIVITE
    // =========================
    @Override
    public ActiviteResponseDTO updateActivite(Long id, ActiviteRequestDTO dto) {

        User connectedUser = getConnectedUser();

        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        activite.setTitre(dto.getTitre());
        activite.setDescription(dto.getDescription());
        activite.setType(dto.getType());
        activite.setStatut(dto.getStatut());
        activite.setMembres(dto.getMembre());
        activite.setStatutProposition(dto.getStatutProposition());

        Activite updated = activiteRepository.save(activite);

        // ✅ HISTORIQUE UPDATE ACTIVITE
        historiqueService.saveHistorique(
                TypeAction.ACTIVITE,
                "ACTIVITE_UPDATED",
                updated.getId(),
                updated,
                connectedUser
        );

        if (dto.getStatutProposition() == StatutProposition.POUR_VOTE) {

            Optional<Vote> existingVote = voteRepository.findByActivite(activite);

            if (existingVote.isPresent()) {

                Vote vote = existingVote.get();
                vote.setDescription("Vote pour l'activité: " + activite.getTitre());

                if (dto.getDateLimiteVote() != null) {
                    vote.setDateLimite(dto.getDateLimiteVote());
                }

                voteRepository.save(vote);

                //  HISTORIQUE VOTE UPDATED
                historiqueService.saveHistorique(
                        TypeAction.VOTE,
                        "VOTE_UPDATED",
                        vote.getId(),
                        activite,
                        connectedUser
                );

            } else {

                Vote vote = Vote.builder()
                        .description("Vote pour l'activité: " + activite.getTitre())
                        .activite(activite)
                        .dateLimite(dto.getDateLimiteVote())
                        .build();

                Vote savedVote = voteRepository.save(vote);

                //  HISTORIQUE VOTE CREATED
                historiqueService.saveHistorique(
                        TypeAction.VOTE,
                        "VOTE_CREATED",
                        savedVote.getId(),
                        activite,
                        connectedUser
                );
            }
        }

        if (activite.getMembres() != null && !activite.getMembres().isEmpty()) {
            activite.getMembres().stream()
                    .distinct()
                    .forEach(email ->
                            emailNotifsService.envoyerEmail(
                                    email,
                                    activite.getTitre(),
                                    activite.getDescription()
                            )
                    );
        }

        return activiteMapper.toDto(updated);
    }

    // =========================
    // DELETE
    // =========================
    @Override
    @Transactional
    public void delete(Long id) {

        User connectedUser = getConnectedUser();

        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        // ✅ HISTORIQUE DELETE
        historiqueService.saveHistorique(
                TypeAction.ACTIVITE,
                "ACTIVITE_DELETED",
                activite.getId(),
                activite,
                connectedUser
        );

        activiteRepository.delete(activite);
    }

    // =========================
    // INVITES
    // =========================
    @Override
    public List<ActiviteResponseDTO> getActivitiesInvite() {
        return activiteRepository.getActivitiesInvite()
                .stream()
                .map(activiteMapper::toDto)
                .toList();
    }
}