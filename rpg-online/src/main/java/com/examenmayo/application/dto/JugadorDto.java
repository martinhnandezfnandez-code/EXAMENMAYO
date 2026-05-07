package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record JugadorRequest(
    @NotBlank String nickname
) {}

public record JugadorResponse(
    UUID id,
    String nickname,
    int nivel,
    long experiencia,
    BigDecimal oro,
    UUID clanId,
    String clanNombre,
    List<PersonajeResponse> personajes
) {}
