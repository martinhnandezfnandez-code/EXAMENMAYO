package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    private Jugador jugador;
    private Personaje personaje;

    @BeforeEach
    void setUp() {
        jugador = Jugador.builder()
                .nickname("TestPlayer")
                .nivel(1)
                .experiencia(0)
                .oro(BigDecimal.valueOf(100))
                .build();

        personaje = Personaje.builder()
                .nombre("Hero")
                .clase(ClasePersonaje.GUERRERO)
                .vida(100)
                .mana(50)
                .ataque(15)
                .defensa(10)
                .jugador(jugador)
                .build();
        Inventario inv = new Inventario();
        inv.setPersonaje(personaje);
        personaje.setInventario(inv);
    }

    @Test
    void shouldSubirNivel() {
        jugador.subirNivel();
        assertEquals(2, jugador.getNivel());
        assertEquals(0, jugador.getExperiencia());
    }

    @Test
    void shouldUnirseClan() {
        Clan clan = Clan.builder().nombre("TestClan").lider(jugador).build();
        jugador.unirseClan(clan);
        assertEquals(clan, jugador.getClan());
        assertTrue(clan.getMiembros().contains(jugador));
    }

    @Test
    void shouldAgregarExperienciaYSubirNivel() {
        jugador.agregarExperiencia(150);
        assertEquals(2, jugador.getNivel());
        assertEquals(50, jugador.getExperiencia());
    }

    @Test
    void shouldAgregarOro() {
        jugador.agregarOro(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), jugador.getOro());
    }

    @Test
    void shouldRestarOro() {
        jugador.restarOro(BigDecimal.valueOf(30));
        assertEquals(BigDecimal.valueOf(70), jugador.getOro());
    }

    @Test
    void shouldThrowWhenOroInsuficiente() {
        assertThrows(IllegalStateException.class, () ->
                jugador.restarOro(BigDecimal.valueOf(200)));
    }

    @Test
    void shouldIniciarCombate() {
        Personaje otro = Personaje.builder()
                .nombre("Enemy")
                .clase(ClasePersonaje.MAGO)
                .jugador(jugador)
                .build();
        Combate combate = jugador.iniciarCombate(personaje, otro);
        assertNotNull(combate);
        assertEquals(2, combate.getParticipantes().size());
    }
}
