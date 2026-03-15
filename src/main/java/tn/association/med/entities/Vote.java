package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;
import tn.association.med.enums.VoteStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreation;

    private String description;

    private Date dateLimite;

    @Enumerated(EnumType.STRING)
    private VoteStatus statut;
    
    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        this.statut = VoteStatus.OUVERT;
    }

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<ParticipationVote> participations;
    
    
}