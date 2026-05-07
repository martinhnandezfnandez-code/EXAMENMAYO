package com.examenmayo.domain.experience;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExperienceManagerTest {

    @Test
    void shouldCalculateExpForLevel1() {
        ExperienceManager manager = new ExperienceManager(new QuadraticExpStrategy(100));
        assertEquals(100, manager.getExpRequerida(1));
        assertEquals(400, manager.getExpRequerida(2));
    }

    @Test
    void shouldGainExpAndLevelUp() {
        ExperienceManager manager = new ExperienceManager(new QuadraticExpStrategy(100));
        ExperienceManager.ExpResult result = manager.ganarExperiencia(0, 1, 350);
        assertEquals(50, result.experienciaRestante());
        assertEquals(1, result.nivelesSubidos());
    }

    @Test
    void shouldGainExpMultipleLevels() {
        ExperienceManager manager = new ExperienceManager(new QuadraticExpStrategy(100));
        ExperienceManager.ExpResult result = manager.ganarExperiencia(0, 1, 1500);
        assertEquals(0, result.experienciaRestante());
        assertEquals(2, result.nivelesSubidos());
    }

    @Test
    void shouldNotLevelUpWithLittleExp() {
        ExperienceManager manager = new ExperienceManager(new QuadraticExpStrategy(100));
        ExperienceManager.ExpResult result = manager.ganarExperiencia(0, 1, 50);
        assertEquals(50, result.experienciaRestante());
        assertEquals(0, result.nivelesSubidos());
    }
}
