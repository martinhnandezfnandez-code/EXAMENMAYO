package com.examenmayo.application.dto;

import java.util.List;
import java.util.UUID;

public record InventarioResponse(
    UUID id,
    int capacidadMaxima,
    int objetosUsados,
    List<ObjetoResponse> objetos
) {}
