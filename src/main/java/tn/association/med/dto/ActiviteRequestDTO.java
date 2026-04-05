package tn.association.med.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tn.association.med.enums.StatutActivite;
import tn.association.med.enums.StatutProposition;
import tn.association.med.enums.TypeActivite;

@Data
@Getter
@Setter
public class ActiviteRequestDTO {

    private String titre;
    private String description;
    private TypeActivite type;
    private List<String> membre;
    private StatutActivite statut;
    private StatutProposition statutProposition;
    private Boolean envoyerATous;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateLimiteVote;
}