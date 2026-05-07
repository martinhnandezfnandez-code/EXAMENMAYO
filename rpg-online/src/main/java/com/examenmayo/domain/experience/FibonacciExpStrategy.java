package com.examenmayo.domain.experience;

public class FibonacciExpStrategy implements ExperienceStrategy {
    private final long base;

    public FibonacciExpStrategy(long base) {
        this.base = base;
    }

    public FibonacciExpStrategy() {
        this(50);
    }

    @Override
    public long calcularExpRequerida(int nivel) {
        return fibonacci(nivel) * base;
    }

    private long fibonacci(int n) {
        if (n <= 1) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
}
