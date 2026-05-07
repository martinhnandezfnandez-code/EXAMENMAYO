package com.examenmayo.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
