package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import java.util.*;

public class SkillFactory {

    public static List<Habilidad> crearHabilidadesParaClase(ClasePersonaje clase) {
        return switch (clase) {
            case GUERRERO -> crearHabilidadesGuerrero();
            case MAGO -> crearHabilidadesMago();
            case ARQUERO -> crearHabilidadesArquero();
            case PALADIN -> crearHabilidadesPaladin();
            case ASESINO -> crearHabilidadesAsesino();
        };
    }

    private static List<Habilidad> crearHabilidadesGuerrero() {
        return List.of(
            new HabilidadOfensiva("Golpe Poderoso", "Ataque devastador con el arma", 20, 30, 2, 1, ClasePersonaje.GUERRERO, 1.5),
            new HabilidadOfensiva("Tajo Giratorio", "Gira el arma causando daño a todos", 35, 45, 3, 3, ClasePersonaje.GUERRERO, 2.0),
            new HabilidadBuff("Escudo Defensivo", "Aumenta la defensa del personaje", 15, 20, 4, 2, ClasePersonaje.GUERRERO, EstadoEfecto.PROTEGIDO, 3),
            new HabilidadOfensiva("Furia del Guerrero", "Ataque furioso que ignora defensa", 50, 60, 5, 5, ClasePersonaje.GUERRERO, 2.5),
            new HabilidadCurativa("Resistencia", "Recupera vida usando su fuerza", 25, 30, 4, 4, ClasePersonaje.GUERRERO, 1.2)
        );
    }

    private static List<Habilidad> crearHabilidadesMago() {
        return List.of(
            new HabilidadOfensiva("Bola de Fuego", "Lanza una bola de fuego ardiente", 30, 40, 2, 1, ClasePersonaje.MAGO, 1.8),
            new HabilidadOfensiva("Rayo Helado", "Congela al enemigo con hielo", 25, 35, 2, 3, ClasePersonaje.MAGO, 1.6),
            new HabilidadCurativa("Toque de Vida", "Cura con energía mágica", 20, 40, 3, 2, ClasePersonaje.MAGO, 1.5),
            new HabilidadBuff("Escudo Arcano", "Protege con barrera mágica", 30, 25, 4, 4, ClasePersonaje.MAGO, EstadoEfecto.PROTEGIDO, 3),
            new HabilidadOfensiva("Tormenta de Fuego", "Devastación elemental total", 60, 80, 6, 6, ClasePersonaje.MAGO, 3.0)
        );
    }

    private static List<Habilidad> crearHabilidadesArquero() {
        return List.of(
            new HabilidadOfensiva("Flecha Precisa", "Disparo certero que atraviesa armadura", 15, 25, 1, 1, ClasePersonaje.ARQUERO, 1.4),
            new HabilidadOfensiva("Lluvia de Flechas", "Múltiples flechas caen del cielo", 30, 35, 3, 3, ClasePersonaje.ARQUERO, 2.2),
            new HabilidadBuff("Ojo de Águila", "Aumenta el ataque drásticamente", 20, 15, 3, 2, ClasePersonaje.ARQUERO, EstadoEfecto.FORTALECIDO, 3),
            new HabilidadOfensiva("Disparo Penetrante", "Ignora toda defensa enemiga", 40, 50, 4, 5, ClasePersonaje.ARQUERO, 2.8),
            new HabilidadCurativa("Aliento de Vida", "Meditación que recupera energía", 15, 25, 3, 4, ClasePersonaje.ARQUERO, 1.0)
        );
    }

    private static List<Habilidad> crearHabilidadesPaladin() {
        return List.of(
            new HabilidadOfensiva("Espada Sagrada", "Ataque bendecido con luz divina", 25, 35, 2, 1, ClasePersonaje.PALADIN, 1.6),
            new HabilidadCurativa("Luz Sanadora", "Poder divino que restaura salud", 30, 50, 3, 2, ClasePersonaje.PALADIN, 1.8),
            new HabilidadBuff("Escudo de la Fe", "Barrera divina de protección", 20, 30, 4, 3, ClasePersonaje.PALADIN, EstadoEfecto.PROTEGIDO, 4),
            new HabilidadOfensiva("Castigo Divino", "Poder celestial devastador", 45, 55, 5, 5, ClasePersonaje.PALADIN, 2.4),
            new HabilidadCurativa("Sacrificio", "Sacrifica vida para sanar a todos", 40, 80, 6, 6, ClasePersonaje.PALADIN, 2.0)
        );
    }

    private static List<Habilidad> crearHabilidadesAsesino() {
        return List.of(
            new HabilidadOfensiva("Ataque Furtivo", "Golpe letal desde las sombras", 10, 40, 2, 1, ClasePersonaje.ASESINO, 2.0),
            new HabilidadOfensiva("Golpe Certero", "Apunta a puntos débiles", 20, 35, 2, 3, ClasePersonaje.ASESINO, 1.8),
            new HabilidadBuff("Sigilo", "Se vuelve invisible y esquiva ataques", 25, 10, 4, 2, ClasePersonaje.ASESINO, EstadoEfecto.FORTALECIDO, 2),
            new HabilidadBuff("Venenoso", "Envenena al enemigo causando daño continuo", 15, 20, 3, 4, ClasePersonaje.ASESINO, EstadoEfecto.ENVENENADO, 3),
            new HabilidadOfensiva("Golpe Mortal", "Ejecución letal con daño masivo", 35, 70, 6, 6, ClasePersonaje.ASESINO, 3.5)
        );
    }
}
