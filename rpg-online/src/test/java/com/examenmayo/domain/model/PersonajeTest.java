package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonajeTest {

    private Personaje personaje;
    private Personaje enemigo;
    private Inventario inventario;

    @BeforeEach
    void setUp() {
        Jugador jugador = Jugador.builder().nickname("Player").build();

        personaje = Personaje.builder()
                .nombre("Hero")
                .clase(ClasePersonaje.GUERRERO)
                .vida(100)
                .mana(50)
                .ataque(20)
                .defensa(10)
                .jugador(jugador)
                .build();

        inventario = new Inventario();
        inventario.setPersonaje(personaje);
        personaje.setInventario(inventario);

        enemigo = Personaje.builder()
                .nombre("Enemy")
                .clase(ClasePersonaje.MAGO)
                .vida(80)
                .mana(100)
                .ataque(15)
                .defensa(5)
                .jugador(jugador)
                .build();
        Inventario invEnemy = new Inventario();
        invEnemy.setPersonaje(enemigo);
        enemigo.setInventario(invEnemy);
    }

    @Test
    void shouldCalcularPoderTotal() {
        int poder = personaje.calcularPoderTotal();
        assertEquals(100 + 50 + 40 + 20, poder);
    }

    @Test
    void shouldCalcularPoderTotalConEquipo() {
        Arma espada = Arma.builder()
                .nombre("Espada")
                .rareza(Rareza.RARO)
                .valor(100)
                .danio(15)
                .build();
        inventario.agregarObjeto(espada);

        int poder = personaje.calcularPoderTotal();
        assertEquals(100 + 50 + 40 + 20 + 15, poder);
    }

    @Test
    void shouldAtacarConEstrategia() {
        DamageStrategy strategy = new PhysicalDamageStrategy();
        int danio = personaje.atacar(enemigo, strategy);
        assertEquals(Math.max(1, 20 - 5), danio);
    }

    @Test
    void shouldRecibirDanio() {
        personaje.recibirDanio(30);
        assertEquals(80, personaje.getVida());
    }

    @Test
    void shouldRecibirDanioMinimo() {
        personaje.recibirDanio(5);
        assertEquals(95, personaje.getVida());
    }

    @Test
    void shouldUsarConsumible() {
        Consumible pocion = Consumible.builder()
                .nombre("Pocion")
                .rareza(Rareza.COMUN)
                .valor(50)
                .efecto(TipoEfecto.CURACION)
                .potencia(30)
                .build();
        inventario.agregarObjeto(pocion);
        personaje.setVida(50);

        personaje.usarObjeto(pocion);
        assertEquals(80, personaje.getVida());
        assertFalse(inventario.listarObjetos().contains(pocion));
    }

    @Test
    void shouldEstaVivo() {
        assertTrue(personaje.estaVivo());
        personaje.setVida(0);
        assertFalse(personaje.estaVivo());
    }
}
