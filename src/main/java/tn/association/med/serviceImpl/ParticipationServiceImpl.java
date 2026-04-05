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
import tn.association.med.serviceImpl.notification.EmailNotifsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ActiviteRepository activiteRepository;
    private final ParticipationMapper participationMapper;
    private final EmailNotifsService emailNotifsService;


    @Override
    public ParticipationResponseDTO create(ParticipationRequestDTO dto) {

        Activite activite = activiteRepository.findById(dto.getActiviteId())
                .orElseThrow(() -> new RuntimeException("Activite non trouvée"));

        // contrôle email
        if (participationRepository.existsByemailParticipant(dto.getEmailParticipant())) {
            throw new RuntimeException("Une demande de participation a déjà été effectuée avec cet email. Votre demande a bien été reçue.");
        }

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
        Participation participation = participationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participation introuvable"));

        // Récupérer l'email avant suppression
        String email = participation.getEmailParticipant();

        // Supprimer la participation
        participationRepository.delete(participation);

        // Envoyer un email de notification
        String subject = "Mise à jour de votre demande de participation";

        String message = "Bonjour,\n\n"
                + "Nous vous remercions pour l’intérêt que vous portez à l’activité : "
                + participation.getActivite().getTitre()
                + ".\n"
                + "Cependant, nous vous informons que nous ne sommes plus en mesure d’accepter de nouvelles participations.\n\n"
                + "Merci pour votre compréhension.\n\n"
                + "Cordialement,\nAssociation Médicale de Ben Gardane";

        emailNotifsService.envoyerEmail(email, subject, message);
    }
}