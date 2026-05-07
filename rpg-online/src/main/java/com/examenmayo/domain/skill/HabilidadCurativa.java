package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.TipoHabilidad;

public class HabilidadCurativa extends Habilidad {
    private final double multiplicadorCuracion;

    public HabilidadCurativa(String nombre, String descripcion, int costeMana,
                             int curacionBase, int cooldown, int nivelRequerido,
                             ClasePersonaje clasePermitida, double multiplicadorCuracion) {
        super(nombre, descripcion, TipoHabilidad.CURATIVA, costeMana, curacionBase,
              cooldown, nivelRequerido, clasePermitida);
        this.multiplicadorCuracion = multiplicadorCuracion;
    }

    @Override
    public int calcularDanio(int ataqueBase, int nivel) {
        return (int) ((getDanioBase() + ataqueBase / 2) * multiplicadorCuracion);
    }
}
