package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomParticipant;

    private String emailParticipant;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;
}