package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CombateRequest(
    @NotNull UUID atacanteId,
    @NotNull UUID defensorId,
    String estrategia
) {}
