package com.examenmayo.domain.experience;

public class LinearExpStrategy implements ExperienceStrategy {
    private final long constante;

    public LinearExpStrategy(long constante) {
        this.constante = constante;
    }

    public LinearExpStrategy() {
        this(200);
    }

    @Override
    public long calcularExpRequerida(int nivel) {
        return (long) nivel * constante;
    }
}
