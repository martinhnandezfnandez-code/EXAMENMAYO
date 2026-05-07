package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.CombateRequest;
import com.examenmayo.application.dto.CombateResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.model.Combate;
import com.examenmayo.domain.service.CombatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/combats")
@RequiredArgsConstructor
public class CombatController {

    private final CombatService combatService;
    private final RpgMapper mapper;

    @GetMapping
    public ResponseEntity<List<CombateResponse>> findAll() {
        List<CombateResponse> response = combatService.findAll().stream()
                .map(mapper::toCombateResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CombateResponse> findById(@PathVariable UUID id) {
        Combate combate = combatService.findById(id);
        return ResponseEntity.ok(mapper.toCombateResponse(combate));
    }

    @PostMapping
    public ResponseEntity<CombateResponse> iniciar(@Valid @RequestBody CombateRequest request) {
        Combate combate = combatService.iniciarCombate(
                request.atacanteId(),
                request.defensorId(),
                request.estrategia()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCombateResponse(combate));
    }
}
