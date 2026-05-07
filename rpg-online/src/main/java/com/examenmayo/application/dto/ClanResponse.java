package com.examenmayo.application.dto;

import java.util.UUID;

public record ClanResponse(
    UUID id,
    String nombre,
    UUID liderId,
    String liderNickname,
    int numeroMiembros,
    int ranking
) {}
