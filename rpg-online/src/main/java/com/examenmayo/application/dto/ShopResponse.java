package com.examenmayo.application.dto;

import java.util.UUID;

public record ShopResponse(
    UUID jugadorId,
    String jugadorNickname,
    UUID objetoId,
    String objetoNombre,
    int precio,
    boolean exitoso,
    String mensaje
) {}
