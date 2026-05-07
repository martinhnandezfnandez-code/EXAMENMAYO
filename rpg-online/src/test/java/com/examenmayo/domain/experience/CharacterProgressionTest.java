package com.examenmayo.domain.experience;

import com.examenmayo.domain.enums.ClasePersonaje;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterProgressionTest {

    @Test
    void shouldStartAtLevel1() {
        CharacterProgression prog = new CharacterProgression(ClasePersonaje.GUERRERO);
        assertEquals(1, prog.getNivel());
        assertEquals(0, prog.getExperienciaActual());
        assertEquals(100, prog.getExperienciaNecesaria());
    }

    @Test
    void shouldGainExperience() {
        CharacterProgression prog = new CharacterProgression(ClasePersonaje.GUERRERO);
        LevelUpReward reward = prog.ganarExperiencia(150, ClasePersonaje.GUERRERO);
        assertNotNull(reward);
        assertEquals(2, prog.getNivel());
        assertEquals(50, prog.getExperienciaActual());
        assertEquals(400, prog.getExperienciaNecesaria());
    }

    @Test
    void shouldUnlockSkillAtLevel1() {
        CharacterProgression prog = new CharacterProgression(ClasePersonaje.MAGO);
        assertFalse(prog.getTodasLasHabilidades().isEmpty());
    }

    @Test
    void shouldGetSkillLevelRequirements() {
        CharacterProgression prog = new CharacterProgression(ClasePersonaje.ARQUERO);
        var skills = prog.getTodasLasHabilidades();
        assertTrue(skills.stream().anyMatch(s -> s.getNivelRequerido() == 1));
    }
}
