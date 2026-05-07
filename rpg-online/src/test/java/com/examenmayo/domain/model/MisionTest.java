package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.EstadoMision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MisionTest {

    private Mision mision;
    private Jugador jugador;

    @BeforeEach
    void setUp() {
        mision = Mision.builder()
                .nombre("Test Mission")
                .descripcion("Desc")
                .recompensa(100)
                .estado(EstadoMision.PENDIENTE)
                .build();

        jugador = Jugador.builder()
                .nickname("Player")
                .nivel(1)
                .experiencia(0)
                .oro(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    void shouldAsignarJugador() {
        mision.asignarJugador(jugador);
        assertEquals(EstadoMision.EN_PROGRESO, mision.getEstado());
        assertTrue(mision.getJugadores().contains(jugador));
    }

    @Test
    void shouldCompletarMision() {
        mision.asignarJugador(jugador);
        mision.completar();
        assertEquals(EstadoMision.COMPLETADA, mision.getEstado());
    }

    @Test
    void shouldThrowWhenCompletarSinAsignar() {
        assertThrows(IllegalStateException.class, () -> mision.completar());
    }
}
