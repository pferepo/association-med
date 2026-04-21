package tn.association.med.service;

import org.springframework.stereotype.Service;

import tn.association.med.entities.Activite;
import tn.association.med.entities.Historique;
import tn.association.med.entities.User;
import tn.association.med.enums.TypeAction;

import java.util.List;

@Service
public interface HistoriqueService {



        void saveHistorique(
                TypeAction type,
                String action,
                Long referenceId,
                Activite activite,
                User user
        );

    List<Historique> findAll();

    void deleteHistorique(Long id);
}