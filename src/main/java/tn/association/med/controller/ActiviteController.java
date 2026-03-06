package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.ActiviteRequestDTO;
import tn.association.med.dto.ActiviteResponseDTO;
import tn.association.med.service.ActiviteService;

import java.util.List;

@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
public class ActiviteController {

    private final ActiviteService activiteService;

    @PostMapping
    public ActiviteResponseDTO create(@RequestBody ActiviteRequestDTO dto) {
        return activiteService.create(dto);
    }

    @GetMapping
    public List<ActiviteResponseDTO> getAll() {
        return activiteService.getAll();
    }

    @GetMapping("/{id}")
    public ActiviteResponseDTO getById(@PathVariable Long id) {
        return activiteService.getById(id);
    }

    @PutMapping("/{id}")
    public ActiviteResponseDTO update(@PathVariable Long id,
                                      @RequestBody ActiviteRequestDTO dto) {

        return activiteService.updateActivite(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        activiteService.delete(id);
    }
}

