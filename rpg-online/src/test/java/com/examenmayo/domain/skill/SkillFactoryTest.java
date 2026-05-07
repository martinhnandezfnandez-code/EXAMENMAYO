package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillFactoryTest {

    @Test
    void shouldCreateWarriorSkills() {
        List<Habilidad> skills = SkillFactory.crearHabilidadesParaClase(ClasePersonaje.GUERRERO);
        assertFalse(skills.isEmpty());
        assertEquals(5, skills.size());
        assertTrue(skills.stream().anyMatch(s -> s instanceof HabilidadOfensiva));
        assertTrue(skills.stream().anyMatch(s -> s instanceof HabilidadBuff));
        assertTrue(skills.stream().anyMatch(s -> s instanceof HabilidadCurativa));
    }

    @Test
    void shouldCreateMageSkills() {
        List<Habilidad> skills = SkillFactory.crearHabilidadesParaClase(ClasePersonaje.MAGO);
        assertEquals(5, skills.size());
        assertTrue(skills.stream().allMatch(s -> s.getClasePermitida() == ClasePersonaje.MAGO));
    }

    @Test
    void shouldCreateArcherSkills() {
        List<Habilidad> skills = SkillFactory.crearHabilidadesParaClase(ClasePersonaje.ARQUERO);
        assertEquals(5, skills.size());
        assertTrue(skills.stream().anyMatch(s -> s.getNombre().contains("Flecha")));
    }

    @Test
    void offensiveSkillShouldCalculateDamage() {
        HabilidadOfensiva skill = new HabilidadOfensiva(
                "Test", "Test", 20, 30, 2, 1, ClasePersonaje.GUERRERO, 1.5);
        int dmg = skill.calcularDanio(20, 5);
        assertTrue(dmg > 0);
        assertEquals((int) ((20 + 30) * 1.5 + (5 * 2)), dmg);
    }

    @Test
    void healingSkillShouldCalculateHealing() {
        HabilidadCurativa skill = new HabilidadCurativa(
                "Cura", "Cura", 20, 40, 3, 1, ClasePersonaje.MAGO, 1.5);
        int heal = skill.calcularDanio(20, 5);
        assertTrue(heal > 0);
    }
}
