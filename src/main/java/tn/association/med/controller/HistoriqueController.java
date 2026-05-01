package tn.association.med.controller;

import io.swagger.v3.oas.annotations.Parameter;
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

    @DeleteMapping("/{id}")
    public void deleteHistorique(
            @Parameter(description = "Supprimer Historique par ID", example = "1")
            @PathVariable Long id
    ) {
        historiqueService.deleteHistorique(id);
    }
}