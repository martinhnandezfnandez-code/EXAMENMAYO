package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonajeClaseTest {

    @Test
    void guerreroShouldHaveHighAttackAndDefense() {
        Personaje guerrero = Personaje.builder()
                .nombre("Thor")
                .clase(ClasePersonaje.GUERRERO)
                .vida(100)
                .mana(30)
                .ataque(20)
                .defensa(15)
                .build();
        guerrero.setInventario(new Inventario());
        guerrero.getInventario().setPersonaje(guerrero);

        assertTrue(guerrero.getAtaque() >= 15);
        assertTrue(guerrero.getDefensa() >= 10);
    }

    @Test
    void magoShouldHaveHighMana() {
        Personaje mago = Personaje.builder()
                .nombre("Merlin")
                .clase(ClasePersonaje.MAGO)
                .vida(70)
                .mana(100)
                .ataque(25)
                .defensa(3)
                .build();
        mago.setInventario(new Inventario());
        mago.getInventario().setPersonaje(mago);

        assertEquals(ClasePersonaje.MAGO, mago.getClase());
        assertTrue(mago.getMana() > 50);
    }
}
