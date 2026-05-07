package com.examenmayo.domain.experience;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExperienceManager {
    private final ExperienceStrategy strategy;

    public ExperienceManager(ExperienceStrategy strategy) {
        this.strategy = strategy;
    }

    public ExperienceManager() {
        this(new QuadraticExpStrategy());
    }

    public long getExpRequerida(int nivel) {
        return strategy.calcularExpRequerida(nivel);
    }

    public ExpResult ganarExperiencia(long expActual, int nivelActual, long expGanada) {
        long totalExp = expActual + expGanada;
        long requerida = getExpRequerida(nivelActual);
        int nivelesSubidos = 0;
        long expRestante = totalExp;

        while (expRestante >= requerida) {
            expRestante -= requerida;
            nivelesSubidos++;
            requerida = getExpRequerida(nivelActual + nivelesSubidos);
        }

        return new ExpResult(expRestante, nivelesSubidos);
    }

    public record ExpResult(long experienciaRestante, int nivelesSubidos) {}
}
