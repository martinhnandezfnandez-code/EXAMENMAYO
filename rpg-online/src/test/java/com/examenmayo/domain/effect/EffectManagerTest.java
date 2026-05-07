package com.examenmayo.domain.effect;

import com.examenmayo.domain.enums.EstadoEfecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectManagerTest {

    private EffectManager manager;

    @BeforeEach
    void setUp() {
        manager = new EffectManager();
    }

    @Test
    void shouldApplyEffect() {
        manager.aplicarEfecto(EstadoEfecto.ENVENENADO, 3, 5);
        assertTrue(manager.tieneEfecto(EstadoEfecto.ENVENENADO));
    }

    @Test
    void shouldTickAndExpireEffect() {
        manager.aplicarEfecto(EstadoEfecto.ENVENENADO, 2, 5);
        assertTrue(manager.tieneEfecto(EstadoEfecto.ENVENENADO));
        manager.tickEfectos();
        assertTrue(manager.tieneEfecto(EstadoEfecto.ENVENENADO));
        manager.tickEfectos();
        assertFalse(manager.tieneEfecto(EstadoEfecto.ENVENENADO));
    }

    @Test
    void shouldCalculatePoisonDamage() {
        manager.aplicarEfecto(EstadoEfecto.ENVENENADO, 3, 8);
        assertEquals(8, manager.calcularDanioExtra());
    }

    @Test
    void shouldModifyDefenseWhenProtected() {
        manager.aplicarEfecto(EstadoEfecto.PROTEGIDO, 3, 15);
        assertEquals(15, manager.getModificadorDefensa());
    }

    @Test
    void shouldPreventActionWhenParalyzed() {
        assertTrue(manager.puedeActuar());
        manager.aplicarEfecto(EstadoEfecto.PARALIZADO, 2, 0);
        assertFalse(manager.puedeActuar());
    }

    @Test
    void shouldClearAllEffects() {
        manager.aplicarEfecto(EstadoEfecto.ENVENENADO, 3, 5);
        manager.aplicarEfecto(EstadoEfecto.PROTEGIDO, 3, 10);
        manager.limpiar();
        assertTrue(manager.getEfectosActivos().isEmpty());
    }
}
