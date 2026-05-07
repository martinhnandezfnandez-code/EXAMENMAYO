package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageStrategyTest {

    @Test
    void shouldCalculatePhysicalDamage() {
        Personaje atacante = Personaje.builder().nombre("A").ataque(20).defensa(5).build();
        Personaje defensor = Personaje.builder().nombre("D").ataque(10).defensa(8).build();
        atacante.setInventario(new Inventario()); atacante.getInventario().setPersonaje(atacante);
        defensor.setInventario(new Inventario()); defensor.getInventario().setPersonaje(defensor);

        DamageStrategy physical = new PhysicalDamageStrategy();
        int danio = physical.calcular(atacante, defensor);
        assertEquals(Math.max(1, 20 - 8), danio);
    }

    @Test
    void shouldCalculateMagicDamage() {
        Personaje atacante = Personaje.builder().nombre("A").mana(100).ataque(20).build();
        Personaje defensor = Personaje.builder().nombre("D").defensa(10).build();
        atacante.setInventario(new Inventario()); atacante.getInventario().setPersonaje(atacante);
        defensor.setInventario(new Inventario()); defensor.getInventario().setPersonaje(defensor);

        DamageStrategy magic = new MagicDamageStrategy();
        int danio = magic.calcular(atacante, defensor);
        assertEquals(Math.max(1, 50 - 5), danio);
    }

    @Test
    void shouldCalculateTrueDamage() {
        Personaje atacante = Personaje.builder().nombre("A").ataque(20).build();
        Personaje defensor = Personaje.builder().nombre("D").defensa(100).build();
        atacante.setInventario(new Inventario()); atacante.getInventario().setPersonaje(atacante);
        defensor.setInventario(new Inventario()); defensor.getInventario().setPersonaje(defensor);

        DamageStrategy trueDmg = new TrueDamageStrategy();
        int danio = trueDmg.calcular(atacante, defensor);
        assertEquals(20, danio);
    }
}
