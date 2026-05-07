package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    private Inventario inventario;
    private Personaje personaje;

    @BeforeEach
    void setUp() {
        personaje = Personaje.builder()
                .nombre("Hero")
                .clase(ClasePersonaje.GUERRERO)
                .build();
        inventario = new Inventario();
        inventario.setPersonaje(personaje);
        personaje.setInventario(inventario);
    }

    @Test
    void shouldAgregarObjeto() {
        Arma espada = Arma.builder().nombre("Espada").rareza(Rareza.COMUN).valor(10).danio(5).build();
        inventario.agregarObjeto(espada);
        assertEquals(1, inventario.listarObjetos().size());
        assertEquals(inventario, espada.getInventario());
    }

    @Test
    void shouldEliminarObjeto() {
        Arma espada = Arma.builder().nombre("Espada").rareza(Rareza.COMUN).valor(10).danio(5).build();
        inventario.agregarObjeto(espada);
        inventario.eliminarObjeto(espada);
        assertTrue(inventario.listarObjetos().isEmpty());
        assertNull(espada.getInventario());
    }

    @Test
    void shouldThrowWhenInventarioLleno() {
        inventario.setCapacidadMaxima(1);
        inventario.agregarObjeto(
                Arma.builder().nombre("Espada").rareza(Rareza.COMUN).valor(10).danio(5).build());
        assertThrows(IllegalStateException.class, () ->
                inventario.agregarObjeto(
                        Arma.builder().nombre("Escudo").rareza(Rareza.COMUN).valor(10).danio(2).build()));
    }
}
