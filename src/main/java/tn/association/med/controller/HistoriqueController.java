package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.entities.Historique;
import tn.association.med.service.HistoriqueService;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
@RequiredArgsConstructor
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    @GetMapping
    public List<Historique> getAllHistorique() {
        return historiqueService.findAll();
    }
}