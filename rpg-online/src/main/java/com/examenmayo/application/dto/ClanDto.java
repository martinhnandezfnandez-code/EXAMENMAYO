package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ClanRequest(
    @NotBlank String nombre
) {}

public record ClanResponse(
    UUID id,
    String nombre,
    UUID liderId,
    String liderNickname,
    int numeroMiembros,
    int ranking
) {}
