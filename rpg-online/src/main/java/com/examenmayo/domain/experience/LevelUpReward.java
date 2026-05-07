package com.examenmayo.domain.experience;

import lombok.Getter;

@Getter
public class LevelUpReward {
    private final int vidaGanada;
    private final int manaGanado;
    private final int ataqueGanado;
    private final int defensaGanada;
    private final int puntosHabilidad;

    public LevelUpReward(int vidaGanada, int manaGanado, int ataqueGanado, int defensaGanada, int puntosHabilidad) {
        this.vidaGanada = vidaGanada;
        this.manaGanado = manaGanado;
        this.ataqueGanado = ataqueGanado;
        this.defensaGanada = defensaGanada;
        this.puntosHabilidad = puntosHabilidad;
    }

    public static LevelUpReward DEFAULT = new LevelUpReward(10, 5, 2, 1, 1);
    public static LevelUpReward GUERRERO = new LevelUpReward(15, 3, 3, 2, 1);
    public static LevelUpReward MAGO = new LevelUpReward(5, 10, 3, 1, 1);
    public static LevelUpReward ARQUERO = new LevelUpReward(8, 5, 3, 1, 1);
    public static LevelUpReward PALADIN = new LevelUpReward(12, 6, 2, 3, 1);
    public static LevelUpReward ASESINO = new LevelUpReward(7, 4, 4, 1, 1);
}
