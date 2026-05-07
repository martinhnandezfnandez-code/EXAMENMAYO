package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.EstadoEfecto;
import com.examenmayo.domain.enums.TipoHabilidad;

public class HabilidadBuff extends Habilidad {
    private final EstadoEfecto efecto;
    private final int duracion;

    public HabilidadBuff(String nombre, String descripcion, int costeMana,
                         int poder, int cooldown, int nivelRequerido,
                         ClasePersonaje clasePermitida, EstadoEfecto efecto, int duracion) {
        super(nombre, descripcion, TipoHabilidad.BUFF, costeMana, poder,
              cooldown, nivelRequerido, clasePermitida);
        this.efecto = efecto;
        this.duracion = duracion;
    }

    @Override
    public int calcularDanio(int ataqueBase, int nivel) {
        return getDanioBase() + (nivel * 2);
    }

    public EstadoEfecto getEfecto() { return efecto; }
    public int getDuracion() { return duracion; }
}
