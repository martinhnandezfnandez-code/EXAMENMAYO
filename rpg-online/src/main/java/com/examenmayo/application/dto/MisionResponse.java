package com.examenmayo.application.dto;

import com.examenmayo.domain.enums.EstadoMision;
import java.util.List;
import java.util.UUID;

public record MisionResponse(
    UUID id,
    String nombre,
    String descripcion,
    int recompensa,
    EstadoMision estado,
    List<UUID> jugadoresId
) {}
