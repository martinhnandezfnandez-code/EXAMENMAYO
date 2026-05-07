package com.examenmayo.domain.model;

public class TrueDamageStrategy implements DamageStrategy {
    @Override
    public int calcular(Personaje atacante, Personaje defensor) {
        return Math.max(1, atacante.getAtaque());
    }
}
