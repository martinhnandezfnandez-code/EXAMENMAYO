package com.examenmayo.domain.effect;

import com.examenmayo.domain.enums.EstadoEfecto;
import lombok.Getter;

@Getter
public class StatusEffect {
    private final EstadoEfecto tipo;
    private int duracionRestante;
    private final int poder;

    public StatusEffect(EstadoEfecto tipo, int duracion, int poder) {
        this.tipo = tipo;
        this.duracionRestante = duracion;
        this.poder = poder;
    }

    public boolean estaActivo() {
        return duracionRestante > 0;
    }

    public void tick() {
        duracionRestante--;
    }
}
