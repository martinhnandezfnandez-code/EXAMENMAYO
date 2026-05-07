package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.service.ShopService;
import java.util.*;

public class ShopScreen extends Screen {

    public ShopScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" TIENDA ");

        var jugadores = engine.getDataStore().getPlayers().findAll();
        if (jugadores.isEmpty()) {
            engine.getRenderer().renderError("No hay jugadores registrados");
            waitForInput();
            return;
        }

        engine.getRenderer().renderInfo("", "Jugadores:");
        for (int i = 0; i < jugadores.size(); i++) {
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", jugadores.get(i).getNickname() + " (Oro: " + jugadores.get(i).getOro() + ")");
        }
        System.out.print("  Selecciona jugador: ");
        int optJ = engine.getInput().readIntInRange(1, jugadores.size());
        if (optJ == -1) return;

        Jugador jugador = jugadores.get(optJ - 1);

        engine.getRenderer().renderHeader(" CATÁLOGO ");
        List<Objeto> catalogo = engine.getDataStore().getItems().findAll();
        if (catalogo.isEmpty()) {
            engine.getRenderer().renderInfo("", "Catálogo vacío. Agregando objetos por defecto...");
            agregarObjetosDemo();
            catalogo = engine.getDataStore().getItems().findAll();
        }

        for (int i = 0; i < catalogo.size(); i++) {
            Objeto o = catalogo.get(i);
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", o.getNombre() + " - " + o.getValor() + " oro (" + o.getRareza() + ")");
        }

        System.out.println();
        engine.getRenderer().renderInfo("" + (catalogo.size() + 1) + ".", "Comprar objeto");
        engine.getRenderer().renderInfo("" + (catalogo.size() + 2) + ".", "Volver");
        System.out.print("  Opción: ");
        int opt = engine.getInput().readInt();

        if (opt >= 1 && opt <= catalogo.size()) {
            Objeto compra = catalogo.get(opt - 1);
            if (jugador.getOro().intValue() >= compra.getValor()) {
                jugador.restarOro(java.math.BigDecimal.valueOf(compra.getValor()));
                engine.getDataStore().getPlayers().save(jugador);
                engine.getRenderer().renderSuccess("Has comprado " + compra.getNombre() + " por " + compra.getValor() + " oro!");
            } else {
                engine.getRenderer().renderError("Oro insuficiente!");
            }
            waitForInput();
        }
    }

    private void agregarObjetosDemo() {
        var db = engine.getDataStore();
        db.getItems().save(Arma.builder().nombre("Espada de Hierro").rareza(com.examenmayo.domain.enums.Rareza.COMUN).valor(50).danio(10).build());
        db.getItems().save(Arma.builder().nombre("Arco Largo").rareza(com.examenmayo.domain.enums.Rareza.RARO).valor(150).danio(18).build());
        db.getItems().save(Armadura.builder().nombre("Armadura de Cuero").rareza(com.examenmayo.domain.enums.Rareza.COMUN).valor(40).defensaExtra(5).build());
        db.getItems().save(Consumible.builder().nombre("Poción de Vida").rareza(com.examenmayo.domain.enums.Rareza.COMUN).valor(25)
                .efecto(com.examenmayo.domain.enums.TipoEfecto.CURACION).potencia(30).build());
        db.getItems().save(Consumible.builder().nombre("Poción de Maná").rareza(com.examenmayo.domain.enums.Rareza.COMUN).valor(25)
                .efecto(com.examenmayo.domain.enums.TipoEfecto.MANA).potencia(30).build());
    }
}
