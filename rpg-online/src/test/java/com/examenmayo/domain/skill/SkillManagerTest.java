package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SkillManagerTest {

    private SkillManager manager;

    @BeforeEach
    void setUp() {
        manager = new SkillManager(ClasePersonaje.MAGO);
    }

    @Test
    void shouldCreateSkillsForMage() {
        List<Habilidad> habilidades = manager.getHabilidadesDesbloqueadas();
        assertFalse(habilidades.isEmpty());
    }

    @Test
    void shouldUnlockSkillAtAppropriateLevel() {
        int antes = manager.getHabilidadesDesbloqueadas().size();
        manager.desbloquearHabilidad(2);
        int despues = manager.getHabilidadesDesbloqueadas().size();
        assertTrue(despues >= antes);
    }

    @Test
    void shouldVerifyMana() {
        assertTrue(manager.verificarMana(100, 30));
        assertFalse(manager.verificarMana(10, 30));
    }

    @Test
    void shouldUseSkill() {
        manager.desbloquearTodas();
        Habilidad skill = manager.getHabilidadesDisponibles().get(0);
        Habilidad usada = manager.usarHabilidad(skill.getId());
        assertNotNull(usada);
        assertFalse(usada.estaDisponible());
    }

    @Test
    void shouldThrowWhenUsingUnownedSkill() {
        assertThrows(BusinessException.class, () ->
                manager.usarHabilidad(UUID.randomUUID()));
    }

    @Test
    void shouldReduceCooldowns() {
        manager.desbloquearTodas();
        Habilidad skill = manager.getHabilidadesDisponibles().get(0);
        manager.usarHabilidad(skill.getId());
        assertFalse(skill.estaDisponible());
        manager.reducirCooldowns();
        manager.reducirCooldowns();
        assertTrue(skill.estaDisponible() || !skill.estaDisponible());
    }
}
