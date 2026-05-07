package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.ResultadoCombate;
import java.util.List;
import java.util.UUID;

public record CombateResponse(
    UUID id,
    List<UUID> participantesId,
    int turnos,
    ResultadoCombate resultado,
    UUID ganadorId
) {}
