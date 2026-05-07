package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.EstadoMision;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;

public record MisionRequest(
    @NotBlank String nombre,
    @NotBlank String descripcion,
    @PositiveOrZero int recompensa
) {}

public record MisionResponse(
    UUID id,
    String nombre,
    String descripcion,
    int recompensa,
    EstadoMision estado,
    List<UUID> jugadoresId
) {}
