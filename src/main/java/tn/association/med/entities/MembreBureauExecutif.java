package tn.association.med.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBRE_BUREAU_EXECUTIF")
public class MembreBureauExecutif extends User {
}