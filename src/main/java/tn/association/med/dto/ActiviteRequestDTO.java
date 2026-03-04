package tn.association.med.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiviteRequestDTO {

    private String titre;
    private String description;
    private String type;
}