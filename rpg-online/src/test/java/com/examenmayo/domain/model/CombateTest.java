package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ResultadoCombate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombateTest {

    private Combate combate;
    private Personaje atacante;
    private Personaje defensor;

    @BeforeEach
    void setUp() {
        atacante = Personaje.builder()
                .nombre("Atacante")
                .vida(100)
                .mana(50)
                .ataque(20)
                .defensa(5)
                .build();
        atacante.setInventario(new Inventario());
        atacante.getInventario().setPersonaje(atacante);

        defensor = Personaje.builder()
                .nombre("Defensor")
                .vida(50)
                .mana(30)
                .ataque(10)
                .defensa(3)
                .build();
        defensor.setInventario(new Inventario());
        defensor.getInventario().setPersonaje(defensor);

        combate = Combate.builder()
                .participantes(List.of(atacante, defensor))
                .build();
        combate.setStrategy(new PhysicalDamageStrategy());
    }

    @Test
    void shouldIniciarCombate() {
        combate.iniciarCombate();
        assertEquals(0, combate.getTurnos());
        assertEquals(ResultadoCombate.EMPATE, combate.getResultado());
    }

    @Test
    void shouldEjecutarTurno() {
        combate.iniciarCombate();
        combate.ejecutarTurno();
        assertEquals(1, combate.getTurnos());
    }

    @Test
    void shouldDeterminarGanador() {
        combate.iniciarCombate();
        int maxTurnos = 20;
        for (int i = 0; i < maxTurnos; i++) {
            combate.ejecutarTurno();
            if (combate.getResultado() != ResultadoCombate.EMPATE) break;
        }
        Personaje ganador = combate.determinarGanador();
        assertNotNull(ganador);
        assertTrue(ganador.estaVivo());
    }

    @Test
    void shouldCalcularDanioPhysical() {
        int danio = combate.calcularDanio(atacante, defensor);
        assertEquals(Math.max(1, 20 - 3), danio);
    }

    @Test
    void shouldThrowWhenNotEnoughParticipants() {
        combate.setParticipantes(List.of(atacante));
        assertThrows(IllegalStateException.class, () -> combate.iniciarCombate());
    }
}
