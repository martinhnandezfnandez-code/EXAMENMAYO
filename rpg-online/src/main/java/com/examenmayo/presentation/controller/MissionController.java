package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.MisionRequest;
import com.examenmayo.application.dto.MisionResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.enums.EstadoMision;
import com.examenmayo.domain.model.Mision;
import com.examenmayo.domain.service.MisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MisionService misionService;
    private final RpgMapper mapper;

    @GetMapping
    public ResponseEntity<List<MisionResponse>> findAll() {
        List<MisionResponse> response = misionService.findAll().stream()
                .map(mapper::toMisionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MisionResponse> findById(@PathVariable UUID id) {
        Mision mision = misionService.findById(id);
        return ResponseEntity.ok(mapper.toMisionResponse(mision));
    }

    @PostMapping
    public ResponseEntity<MisionResponse> create(@Valid @RequestBody MisionRequest request) {
        Mision mision = Mision.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .recompensa(request.recompensa())
                .build();
        Mision creada = misionService.create(mision);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toMisionResponse(creada));
    }

    @PostMapping("/{missionId}/assign/{jugadorId}")
    public ResponseEntity<MisionResponse> assign(@PathVariable UUID missionId, @PathVariable UUID jugadorId) {
        Mision mision = misionService.asignarJugador(missionId, jugadorId);
        return ResponseEntity.ok(mapper.toMisionResponse(mision));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<MisionResponse> complete(@PathVariable UUID id) {
        Mision mision = misionService.completar(id);
        return ResponseEntity.ok(mapper.toMisionResponse(mision));
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<MisionResponse>> findByStatus(@PathVariable EstadoMision estado) {
        List<MisionResponse> response = misionService.findByEstado(estado).stream()
                .map(mapper::toMisionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/player/{jugadorId}")
    public ResponseEntity<List<MisionResponse>> findByPlayer(@PathVariable UUID jugadorId) {
        List<MisionResponse> response = misionService.findByJugador(jugadorId).stream()
                .map(mapper::toMisionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        misionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
