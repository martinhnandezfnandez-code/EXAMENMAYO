package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.JugadorRequest;
import com.examenmayo.application.dto.JugadorResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.model.Jugador;
import com.examenmayo.domain.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final RpgMapper mapper;

    @GetMapping
    public ResponseEntity<List<JugadorResponse>> findAll() {
        List<JugadorResponse> response = playerService.findAll().stream()
                .map(mapper::toJugadorResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponse> findById(@PathVariable UUID id) {
        Jugador jugador = playerService.findByIdWithPersonajes(id);
        return ResponseEntity.ok(mapper.toJugadorResponse(jugador));
    }

    @PostMapping
    public ResponseEntity<JugadorResponse> create(@Valid @RequestBody JugadorRequest request) {
        Jugador jugador = Jugador.builder()
                .nickname(request.nickname())
                .build();
        Jugador creado = playerService.create(jugador);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toJugadorResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorResponse> update(@PathVariable UUID id, @Valid @RequestBody JugadorRequest request) {
        Jugador update = Jugador.builder().nickname(request.nickname()).build();
        Jugador actualizado = playerService.update(id, update);
        return ResponseEntity.ok(mapper.toJugadorResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/level-up")
    public ResponseEntity<JugadorResponse> levelUp(@PathVariable UUID id) {
        Jugador jugador = playerService.subirNivel(id);
        return ResponseEntity.ok(mapper.toJugadorResponse(jugador));
    }
}
