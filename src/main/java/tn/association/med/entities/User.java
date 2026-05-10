package tn.association.med.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DiscriminatorFormula;
import tn.association.med.enums.Genre;
import tn.association.med.enums.Role;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
        "case when role in ('ADMIN') then 'ADMIN' " +
                "when role in ('MEMBRE_BUREAU_EXECUTIF') then 'MEMBRE_BUREAU_EXECUTIF' " +
                "else 'MEMBRE_INVITE' end"
)
@DiscriminatorValue("MEMBRE_INVITE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Boolean active;
    private String tel;
    private String grade;
    private String cin;
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.active == null) {
            this.active = false;
        }
    }
}