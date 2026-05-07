package com.examenmayo.domain.experience;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExperienceStrategyTest {

    @Test
    void quadraticExpStrategyShouldCalculateCorrectly() {
        QuadraticExpStrategy strategy = new QuadraticExpStrategy(100);
        assertEquals(100, strategy.calcularExpRequerida(1));
        assertEquals(400, strategy.calcularExpRequerida(2));
        assertEquals(900, strategy.calcularExpRequerida(3));
        assertEquals(1600, strategy.calcularExpRequerida(4));
        assertEquals(2500, strategy.calcularExpRequerida(5));
    }

    @Test
    void linearExpStrategyShouldCalculateCorrectly() {
        LinearExpStrategy strategy = new LinearExpStrategy(200);
        assertEquals(200, strategy.calcularExpRequerida(1));
        assertEquals(400, strategy.calcularExpRequerida(2));
        assertEquals(600, strategy.calcularExpRequerida(3));
    }

    @Test
    void fibonacciExpStrategyShouldCalculateCorrectly() {
        FibonacciExpStrategy strategy = new FibonacciExpStrategy(50);
        assertEquals(0, strategy.calcularExpRequerida(0));
        assertEquals(50, strategy.calcularExpRequerida(1));
        assertEquals(50, strategy.calcularExpRequerida(2));
        assertEquals(100, strategy.calcularExpRequerida(3));
        assertEquals(150, strategy.calcularExpRequerida(4));
    }
}
