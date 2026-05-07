package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ShopRequest(
    @NotNull UUID jugadorId,
    @NotNull UUID objetoId
) {}

public record ShopResponse(
    UUID jugadorId,
    String jugadorNickname,
    UUID objetoId,
    String objetoNombre,
    int precio,
    boolean exitoso,
    String mensaje
) {}
