package com.examenmayo.domain.model;

public class PhysicalDamageStrategy implements DamageStrategy {
    @Override
    public int calcular(Personaje atacante, Personaje defensor) {
        return Math.max(1, atacante.getAtaque() - defensor.getDefensa());
    }
}
