package tn.association.med.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBRE_INVITE")
public class MembreInvite extends User {}