package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.ClasePersonaje;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PersonajeRequest(
    @NotBlank String nombre,
    @NotNull ClasePersonaje clase
) {}

public record PersonajeResponse(
    UUID id,
    String nombre,
    ClasePersonaje clase,
    int vida,
    int mana,
    int ataque,
    int defensa,
    int poderTotal,
    UUID jugadorId,
    InventarioResponse inventario
) {}
