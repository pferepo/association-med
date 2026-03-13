package tn.association.med.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.association.med.dto.ParticipationRequestDTO;
import tn.association.med.dto.ParticipationResponseDTO;
import tn.association.med.service.ParticipationService;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping
    public ParticipationResponseDTO create(@RequestBody ParticipationRequestDTO dto) {
        return participationService.create(dto);
    }

    @GetMapping
    public List<ParticipationResponseDTO> getAll() {
        return participationService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        participationService.delete(id);
    }
}