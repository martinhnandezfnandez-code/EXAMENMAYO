package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.skill.*;
import com.examenmayo.domain.model.*;
import java.util.*;

public class SkillScreen extends Screen {

    public SkillScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" SISTEMA DE HABILIDADES ");

        var personajes = engine.getDataStore().getCharacters().findAll();
        if (personajes.isEmpty()) {
            engine.getRenderer().renderError("No hay personajes");
            waitForInput();
            return;
        }

        for (Personaje p : personajes) {
            engine.getRenderer().renderHeader(" " + p.getNombre() + " - " + p.getClase());
            List<Habilidad> habilidades = SkillFactory.crearHabilidadesParaClase(p.getClase());
            engine.getRenderer().renderInfo("", "Habilidades disponibles:");
            for (Habilidad h : habilidades) {
                engine.getRenderer().renderSeparator();
                engine.getRenderer().renderInfo("  " + h.getNombre(), "(" + h.getTipo() + ")");
                engine.getRenderer().renderInfo("     Descripción", h.getDescripcion());
                engine.getRenderer().renderInfo("     Coste Maná", String.valueOf(h.getCosteMana()));
                engine.getRenderer().renderInfo("     Daño Base", String.valueOf(h.getDanioBase()));
                engine.getRenderer().renderInfo("     Cooldown", String.valueOf(h.getCooldown()));
                engine.getRenderer().renderInfo("     Nivel Req.", String.valueOf(h.getNivelRequerido()));
            }
            engine.getRenderer().renderSeparator();
        }

        waitForInput();
    }
}
