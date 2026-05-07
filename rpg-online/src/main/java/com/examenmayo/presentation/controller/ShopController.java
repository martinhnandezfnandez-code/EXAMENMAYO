package com.examenmayo.presentation.controller;

import com.examenmayo.application.dto.ObjetoRequest;
import com.examenmayo.application.dto.ObjetoResponse;
import com.examenmayo.application.dto.ShopRequest;
import com.examenmayo.application.dto.ShopResponse;
import com.examenmayo.application.mapper.RpgMapper;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final RpgMapper mapper;

    @GetMapping("/catalog")
    public ResponseEntity<List<ObjetoResponse>> catalog() {
        List<ObjetoResponse> response = shopService.listarCatalogo().stream()
                .map(mapper::toObjetoResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/buy")
    public ResponseEntity<ShopResponse> buy(@Valid @RequestBody ShopRequest request) {
        ShopService.ShopTransaction result = shopService.comprarObjeto(request.jugadorId(), request.objetoId());
        ShopResponse response = new ShopResponse(
                result.jugador().getId(),
                result.jugador().getNickname(),
                result.objeto().getId(),
                result.objeto().getNombre(),
                result.objeto().getValor(),
                result.exitoso(),
                result.mensaje()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sell")
    public ResponseEntity<ShopResponse> sell(@Valid @RequestBody ShopRequest request) {
        ShopService.ShopTransaction result = shopService.venderObjeto(request.jugadorId(), request.objetoId());
        ShopResponse response = new ShopResponse(
                result.jugador().getId(),
                result.jugador().getNickname(),
                result.objeto().getId(),
                result.objeto().getNombre(),
                result.objeto().getValor() / 2,
                result.exitoso(),
                result.mensaje()
        );
        return ResponseEntity.ok(response);
    }
}
