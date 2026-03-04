package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.*;
import tn.association.med.service.ActiviteService;

import java.util.List;

@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActiviteController {

    private final ActiviteService service;

    @PostMapping
    public ActiviteResponseDTO create(@RequestBody ActiviteRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ActiviteResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ActiviteResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
