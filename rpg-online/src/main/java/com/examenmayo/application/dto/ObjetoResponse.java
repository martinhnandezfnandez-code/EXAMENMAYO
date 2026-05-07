package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import com.examenmayo.domain.enums.TipoObjeto;
import java.util.UUID;

public record ObjetoResponse(
    UUID id,
    String nombre,
    TipoObjeto tipo,
    Rareza rareza,
    int valor,
    Integer danio,
    Integer defensaExtra,
    TipoEfecto efecto,
    Integer potencia,
    UUID inventarioId
) {}
