package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.ClasePersonaje;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonajeRequest(
    @NotBlank String nombre,
    @NotNull ClasePersonaje clase
) {}
