package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ShopRequest(
    @NotNull UUID jugadorId,
    @NotNull UUID objetoId
) {}
