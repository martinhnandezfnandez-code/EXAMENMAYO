package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class CharacterScreen extends Screen {
    private Jugador jugadorActual;
    private Personaje personajeActual;

    public CharacterScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" PROGRESIÓN DE PERSONAJE ");

        List<Jugador> jugadores = engine.getDataStore().getPlayers().findAll();
        if (jugadores.isEmpty()) {
            engine.getRenderer().renderInfo("", "No hay jugadores. Crea uno nuevo.");
            System.out.println();
            crearJugador();
            return;
        }

        engine.getRenderer().renderInfo("", "Jugadores disponibles:");
        System.out.println();
        for (int i = 0; i < jugadores.size(); i++) {
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", jugadores.get(i).getNickname() + " (Nv." + jugadores.get(i).getNivel() + ")");
        }
        System.out.println();
        engine.getRenderer().renderInfo("" + (jugadores.size() + 1) + ".", "Crear nuevo jugador");
        engine.getRenderer().renderInfo("" + (jugadores.size() + 2) + ".", "Volver al menú");
        System.out.println();
        System.out.print("  Selecciona: ");

        int opt = engine.getInput().readInt();
        if (opt > 0 && opt <= jugadores.size()) {
            jugadorActual = jugadores.get(opt - 1);
            mostrarPersonajes();
        } else if (opt == jugadores.size() + 1) {
            crearJugador();
        }
    }

    private void crearJugador() {
        engine.getRenderer().renderHeader(" CREAR NUEVO JUGADOR ");
        System.out.print("  Nickname: ");
        String nick = engine.getInput().readString();
        if (nick.isBlank()) {
            engine.getRenderer().renderError("Nickname no puede estar vacío");
            waitForInput();
            return;
        }
        jugadorActual = Jugador.builder().nickname(nick).build();
        engine.getDataStore().getPlayers().save(jugadorActual);

        engine.getRenderer().renderHeader(" CREAR PERSONAJE ");
        System.out.println("  Clases disponibles:");
        ClasePersonaje[] clases = ClasePersonaje.values();
        for (int i = 0; i < clases.length; i++) {
            System.out.println("    " + (i + 1) + ". " + clases[i]);
        }
        System.out.print("  Elige clase: ");
        int claseOpt = engine.getInput().readIntInRange(1, clases.length);
        if (claseOpt == -1) {
            engine.getRenderer().renderError("Clase inválida");
            waitForInput();
            return;
        }
        System.out.print("  Nombre del personaje: ");
        String nombre = engine.getInput().readString();

        Personaje personaje = Personaje.builder()
                .nombre(nombre)
                .clase(clases[claseOpt - 1])
                .jugador(jugadorActual)
                .build();
        Inventario inv = new Inventario();
        inv.setPersonaje(personaje);
        personaje.setInventario(inv);

        engine.getDataStore().getCharacters().save(personaje);
        jugadorActual.getPersonajes().add(personaje);
        engine.getDataStore().getPlayers().save(jugadorActual);

        engine.getRenderer().renderSuccess("Personaje " + nombre + " creado!");
        waitForInput();
    }

    private void mostrarPersonajes() {
        var personajes = engine.getDataStore().getCharacters().findAll().stream()
                .filter(p -> p.getJugador() != null && p.getJugador().getId().equals(jugadorActual.getId()))
                .toList();
        if (personajes.isEmpty()) {
            engine.getRenderer().renderInfo("", "No hay personajes para este jugador");
            waitForInput();
            return;
        }

        engine.getRenderer().renderHeader(" PERSONAJES DE " + jugadorActual.getNickname());
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            engine.getRenderer().renderSeparator();
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", p.getNombre());
            engine.getRenderer().renderInfo("     Clase", p.getClase().toString());
            engine.getRenderer().renderStatBar("     Vida", p.getVida(), 100);
            engine.getRenderer().renderStatBar("     Maná", p.getMana(), 100);
            engine.getRenderer().renderInfo("     Ataque", String.valueOf(p.getAtaque()));
            engine.getRenderer().renderInfo("     Defensa", String.valueOf(p.getDefensa()));
            engine.getRenderer().renderInfo("     Poder Total", String.valueOf(p.calcularPoderTotal()));
        }
        engine.getRenderer().renderSeparator();
        waitForInput();
    }
}
