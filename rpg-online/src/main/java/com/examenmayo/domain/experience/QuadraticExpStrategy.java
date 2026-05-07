package com.examenmayo.domain.experience;

public class QuadraticExpStrategy implements ExperienceStrategy {
    private final long constante;

    public QuadraticExpStrategy(long constante) {
        this.constante = constante;
    }

    public QuadraticExpStrategy() {
        this(100);
    }

    @Override
    public long calcularExpRequerida(int nivel) {
        return (long) Math.pow(nivel, 2) * constante;
    }
}
