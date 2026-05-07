package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record MisionRequest(
    @NotBlank String nombre,
    @NotBlank String descripcion,
    @PositiveOrZero int recompensa
) {}
