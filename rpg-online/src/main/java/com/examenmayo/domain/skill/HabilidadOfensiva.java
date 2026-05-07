package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.TipoHabilidad;

public class HabilidadOfensiva extends Habilidad {
    private final double multiplicadorDanio;

    public HabilidadOfensiva(String nombre, String descripcion, int costeMana,
                             int danioBase, int cooldown, int nivelRequerido,
                             ClasePersonaje clasePermitida, double multiplicadorDanio) {
        super(nombre, descripcion, TipoHabilidad.OFENSIVA, costeMana, danioBase,
              cooldown, nivelRequerido, clasePermitida);
        this.multiplicadorDanio = multiplicadorDanio;
    }

    @Override
    public int calcularDanio(int ataqueBase, int nivel) {
        return (int) ((ataqueBase + getDanioBase()) * multiplicadorDanio + (nivel * 2));
    }
}
