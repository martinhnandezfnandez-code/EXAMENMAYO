package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.enums.EstadoMision;
import com.examenmayo.domain.model.*;
import java.util.*;

public class MissionScreen extends Screen {

    public MissionScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" MISIONES ");

        List<Mision> misiones = engine.getDataStore().getMissions().findAll();
        if (misiones.isEmpty()) {
            engine.getRenderer().renderInfo("", "No hay misiones disponibles");
            engine.getRenderer().renderInfo("", "Volver al menú (1) | Crear misión (2): ");
            int opt = engine.getInput().readInt();
            if (opt == 2) crearMision();
            return;
        }

        for (int i = 0; i < misiones.size(); i++) {
            Mision m = misiones.get(i);
            String color = switch (m.getEstado()) {
                case COMPLETADA -> "✔";
                case EN_PROGRESO -> "▶";
                case PENDIENTE -> "⬜";
            };
            engine.getRenderer().renderInfo("  " + (i + 1) + ". " + color, m.getNombre());
            engine.getRenderer().renderInfo("     Estado", m.getEstado().toString());
            engine.getRenderer().renderInfo("     Recompensa", m.getRecompensa() + " EXP");
            engine.getRenderer().renderSeparator();
        }

        System.out.println();
        engine.getRenderer().renderInfo("" + (misiones.size() + 1) + ".", "Crear nueva misión");
        System.out.print("  Opción: ");
        int opt = engine.getInput().readInt();
        if (opt == misiones.size() + 1) {
            crearMision();
        }
    }

    private void crearMision() {
        engine.getRenderer().renderHeader(" CREAR MISIÓN ");
        System.out.print("  Nombre: ");
        String nombre = engine.getInput().readString();
        System.out.print("  Descripción: ");
        String desc = engine.getInput().readString();
        System.out.print("  Recompensa (EXP): ");
        int rec = Math.max(1, engine.getInput().readInt());

        Mision mision = Mision.builder()
                .nombre(nombre)
                .descripcion(desc)
                .recompensa(rec)
                .build();
        engine.getDataStore().getMissions().save(mision);
        engine.getRenderer().renderSuccess("Misión creada: " + nombre);
        waitForInput();
    }
}
