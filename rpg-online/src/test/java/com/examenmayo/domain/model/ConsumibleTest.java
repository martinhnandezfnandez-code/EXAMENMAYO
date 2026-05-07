package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumibleTest {

    @Test
    void shouldCurarPersonaje() {
        Personaje personaje = Personaje.builder().nombre("Hero").vida(30).mana(50).build();
        personaje.setInventario(new Inventario());
        personaje.getInventario().setPersonaje(personaje);

        Consumible pocion = Consumible.builder()
                .nombre("Pocion")
                .rareza(Rareza.COMUN)
                .valor(50)
                .efecto(TipoEfecto.CURACION)
                .potencia(40)
                .build();

        pocion.aplicar(personaje);
        assertEquals(70, personaje.getVida());
    }

    @Test
    void shouldRevivirPersonaje() {
        Personaje personaje = Personaje.builder().nombre("Hero").vida(0).build();
        personaje.setInventario(new Inventario());
        personaje.getInventario().setPersonaje(personaje);

        Consumible revivir = Consumible.builder()
                .nombre("Revivir")
                .rareza(Rareza.EPICO)
                .valor(500)
                .efecto(TipoEfecto.REVIVIR)
                .potencia(50)
                .build();

        revivir.aplicar(personaje);
        assertEquals(50, personaje.getVida());
    }
}
