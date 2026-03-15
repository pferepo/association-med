package tn.association.med.service;

import org.springframework.stereotype.Service;

import tn.association.med.enums.TypeAction;

@Service
public interface HistoriqueService {

    public void save(TypeAction action, String entityName, Long entityId, String description, Long  idUser);
    
}