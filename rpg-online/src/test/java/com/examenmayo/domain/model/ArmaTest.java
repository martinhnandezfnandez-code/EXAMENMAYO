package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.Rareza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmaTest {

    @Test
    void shouldCreateArma() {
        Arma espada = Arma.builder()
                .nombre("Espada de Fuego")
                .rareza(Rareza.EPICO)
                .valor(200)
                .danio(25)
                .build();
        assertEquals("Espada de Fuego", espada.getNombre());
        assertEquals(Rareza.EPICO, espada.getRareza());
        assertEquals(25, espada.getDanio());
    }
}
