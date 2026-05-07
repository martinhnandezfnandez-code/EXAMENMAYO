package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.ClanRequest;
import com.examenmayo.application.dto.ClanResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.model.Clan;
import com.examenmayo.domain.service.ClanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clans")
@RequiredArgsConstructor
public class ClanController {

    private final ClanService clanService;
    private final RpgMapper mapper;

    @GetMapping
    public ResponseEntity<List<ClanResponse>> findAll() {
        List<ClanResponse> response = clanService.findAll().stream()
                .map(mapper::toClanResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClanResponse> findById(@PathVariable UUID id) {
        Clan clan = clanService.findById(id);
        return ResponseEntity.ok(mapper.toClanResponse(clan));
    }

    @PostMapping
    public ResponseEntity<ClanResponse> create(@Valid @RequestBody ClanRequest request, @RequestParam UUID liderId) {
        Clan clan = Clan.builder()
                .nombre(request.nombre())
                .build();
        Clan creado = clanService.create(clan, liderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toClanResponse(creado));
    }

    @PostMapping("/{clanId}/members/{jugadorId}")
    public ResponseEntity<ClanResponse> addMember(@PathVariable UUID clanId, @PathVariable UUID jugadorId) {
        Clan clan = clanService.agregarMiembro(clanId, jugadorId);
        return ResponseEntity.ok(mapper.toClanResponse(clan));
    }

    @DeleteMapping("/{clanId}/members/{jugadorId}")
    public ResponseEntity<ClanResponse> removeMember(@PathVariable UUID clanId, @PathVariable UUID jugadorId) {
        Clan clan = clanService.expulsarMiembro(clanId, jugadorId);
        return ResponseEntity.ok(mapper.toClanResponse(clan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
