package com.examenmayo.domain.model;

public class MagicDamageStrategy implements DamageStrategy {
    @Override
    public int calcular(Personaje atacante, Personaje defensor) {
        return Math.max(1, (atacante.getMana() / 2) - (defensor.getDefensa() / 2));
    }
}
