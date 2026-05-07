package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.ResultadoCombate;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CombateRequest(
    @NotNull UUID atacanteId,
    @NotNull UUID defensorId,
    String estrategia
) {}

public record CombateResponse(
    UUID id,
    List<UUID> participantesId,
    int turnos,
    ResultadoCombate resultado,
    UUID ganadorId
) {}
