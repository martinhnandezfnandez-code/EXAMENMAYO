package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.ClasePersonaje;
import java.util.UUID;

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
