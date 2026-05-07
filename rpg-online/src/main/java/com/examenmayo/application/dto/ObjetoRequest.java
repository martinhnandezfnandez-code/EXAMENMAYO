package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ObjetoRequest(
    @NotBlank String nombre,
    @NotNull TipoObjeto tipo,
    @NotNull Rareza rareza,
    @PositiveOrZero int valor,
    @Positive Integer danio,
    @PositiveOrZero Integer defensaExtra,
    TipoEfecto efecto,
    @Positive Integer potencia
) {}
