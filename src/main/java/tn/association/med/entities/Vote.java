package tn.association.med.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime dateLimite;

    private String statut;
    
    @PrePersist
    public void prePersist() {
        this.dateLimite = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<ParticipationVote> participations;
    
    
}