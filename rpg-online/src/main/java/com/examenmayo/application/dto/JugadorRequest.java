package com.examenmayo.application.dto;

import jakarta.validation.constraints.NotBlank;

public record JugadorRequest(
    @NotBlank String nickname
) {}
