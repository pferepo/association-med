package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public ActiviteResponseDTO create(@RequestBody ActiviteRequestDTO dto) {
        return activiteService.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public List<ActiviteResponseDTO> getAll() {
        return activiteService.getAll();
    }
    
    @GetMapping("/invite")
    @PreAuthorize("hasRole('INVITE')")
    public List<ActiviteResponseDTO> getActivitiesForInvite(){
    	return activiteService.getActivitiesInvite();
    }
    

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBRE')")
    public ActiviteResponseDTO getById(@PathVariable Long id) {
        return activiteService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ActiviteResponseDTO update(@PathVariable Long id,
                                      @RequestBody ActiviteRequestDTO dto) {

        return activiteService.updateActivite(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        activiteService.delete(id);
    }
}

