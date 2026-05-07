package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ClanRequest(
    @NotBlank String nombre
) {}
