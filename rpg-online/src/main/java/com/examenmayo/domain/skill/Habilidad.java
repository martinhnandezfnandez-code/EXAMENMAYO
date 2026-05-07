package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.TipoHabilidad;
import lombok.Getter;
import java.util.UUID;

@Getter
public abstract class Habilidad {
    private final UUID id;
    private final String nombre;
    private final String descripcion;
    private final TipoHabilidad tipo;
    private final int costeMana;
    private final int danioBase;
    private final int cooldown;
    private final int nivelRequerido;
    private final ClasePersonaje clasePermitida;

    private int cooldownRestante;

    protected Habilidad(String nombre, String descripcion, TipoHabilidad tipo,
                        int costeMana, int danioBase, int cooldown,
                        int nivelRequerido, ClasePersonaje clasePermitida) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costeMana = costeMana;
        this.danioBase = danioBase;
        this.cooldown = cooldown;
        this.nivelRequerido = nivelRequerido;
        this.clasePermitida = clasePermitida;
        this.cooldownRestante = 0;
    }

    public boolean estaDisponible() {
        return cooldownRestante <= 0;
    }

    public void usar() {
        this.cooldownRestante = this.cooldown;
    }

    public void reducirCooldown() {
        if (this.cooldownRestante > 0) {
            this.cooldownRestante--;
        }
    }

    public abstract int calcularDanio(int ataqueBase, int nivel);
}
