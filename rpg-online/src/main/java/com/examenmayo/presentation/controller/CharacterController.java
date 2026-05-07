package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.PersonajeRequest;
import com.examenmayo.application.dto.PersonajeResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.model.Personaje;
import com.examenmayo.domain.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final RpgMapper mapper;

    @GetMapping
    public ResponseEntity<List<PersonajeResponse>> findAll() {
        List<PersonajeResponse> response = characterService.findAll().stream()
                .map(mapper::toPersonajeResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponse> findById(@PathVariable UUID id) {
        Personaje personaje = characterService.findById(id);
        return ResponseEntity.ok(mapper.toPersonajeResponse(personaje));
    }

    @PostMapping("/player/{jugadorId}")
    public ResponseEntity<PersonajeResponse> create(@PathVariable UUID jugadorId, @Valid @RequestBody PersonajeRequest request) {
        Personaje personaje = Personaje.builder()
                .nombre(request.nombre())
                .clase(request.clase())
                .build();
        Personaje creado = characterService.create(jugadorId, personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toPersonajeResponse(creado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{characterId}/use-item/{objetoId}")
    public ResponseEntity<PersonajeResponse> useItem(@PathVariable UUID characterId, @PathVariable UUID objetoId) {
        Personaje personaje = characterService.usarObjeto(characterId, objetoId);
        return ResponseEntity.ok(mapper.toPersonajeResponse(personaje));
    }
}
