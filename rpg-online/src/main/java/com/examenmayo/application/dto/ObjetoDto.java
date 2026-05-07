package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;

public record ObjetoRequest(
    @NotBlank String nombre,
    @NotNull TipoObjeto tipo,
    @NotNull Rareza rareza,
    @PositiveOrZero int valor,
    @Positive Integer danio,
    @PositiveOrZero Integer defensaExtra,
    TipoEfecto efecto,
    @Positive Integer potencia
) {}

public record ObjetoResponse(
    UUID id,
    String nombre,
    TipoObjeto tipo,
    Rareza rareza,
    int valor,
    Integer danio,
    Integer defensaExtra,
    TipoEfecto efecto,
    Integer potencia,
    UUID inventarioId
) {}

public record InventarioResponse(
    UUID id,
    int capacidadMaxima,
    int objetosUsados,
    List<ObjetoResponse> objetos
) {}
