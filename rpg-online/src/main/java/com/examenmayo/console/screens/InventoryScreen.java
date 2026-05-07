package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.model.*;
import java.util.*;

public class InventoryScreen extends Screen {

    public InventoryScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" INVENTARIO ");

        var personajes = engine.getDataStore().getCharacters().findAll();
        if (personajes.isEmpty()) {
            engine.getRenderer().renderInfo("", "No hay personajes creados");
            waitForInput();
            return;
        }

        for (Personaje p : personajes) {
            engine.getRenderer().renderHeader(" " + p.getNombre() + " (" + p.getClase() + ") ");
            if (p.getInventario() != null) {
                List<Objeto> objetos = p.getInventario().listarObjetos();
                if (objetos.isEmpty()) {
                    engine.getRenderer().renderInfo("", "Sin objetos");
                } else {
                    for (Objeto o : objetos) {
                        String tipo = o instanceof Arma ? "⚔" : o instanceof Armadura ? "🛡" : "🧪";
                        engine.getRenderer().renderInfo("  " + tipo, o.getNombre() + " (" + o.getRareza() + ") Valor: " + o.getValor());
                        if (o instanceof Arma a) engine.getRenderer().renderInfo("     Daño", String.valueOf(a.getDanio()));
                        if (o instanceof Armadura a) engine.getRenderer().renderInfo("     Defensa", String.valueOf(a.getDefensaExtra()));
                        if (o instanceof Consumible c) engine.getRenderer().renderInfo("     Efecto", c.getEfecto() + " Pot: " + c.getPotencia());
                    }
                }
            }
            engine.getRenderer().renderSeparator();
        }

        waitForInput();
    }
}
