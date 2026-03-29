package tn.association.med.service;

import org.springframework.stereotype.Service;

import tn.association.med.entities.Historique;
import tn.association.med.enums.TypeAction;

import java.util.List;

@Service
public interface HistoriqueService {

    public void save(TypeAction action, String entityName, Long entityId, String description, Long  idUser);
    List<Historique> findAll();
}