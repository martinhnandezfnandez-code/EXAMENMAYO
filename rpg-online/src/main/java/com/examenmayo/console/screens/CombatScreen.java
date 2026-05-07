package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.experience.CharacterProgression;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.skill.*;
import java.util.*;

public class CombatScreen extends Screen {
    private Personaje atacante;
    private Personaje defensor;
    private CharacterProgression progAtacante;
    private int turno;

    public CombatScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderTitle(" COMBATE ");

        var personajes = engine.getDataStore().getCharacters().findAll();
        if (personajes.size() < 2) {
            engine.getRenderer().renderError("Se necesitan al menos 2 personajes");
            waitForInput();
            return;
        }

        engine.getRenderer().renderInfo("", "Selecciona tu personaje:");
        for (int i = 0; i < personajes.size(); i++) {
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", personajes.get(i).getNombre() + " (" + personajes.get(i).getClase() + " Nv." + "1)");
        }
        System.out.print("  Elige: ");
        int opt1 = engine.getInput().readIntInRange(1, personajes.size());
        if (opt1 == -1) return;

        List<Integer> restantes = new ArrayList<>();
        for (int i = 0; i < personajes.size(); i++) if (i != opt1 - 1) restantes.add(i + 1);
        engine.getRenderer().renderInfo("", "Selecciona tu oponente:");
        for (int i = 0; i < restantes.size(); i++) {
            engine.getRenderer().renderInfo("  " + (i + 1) + ".", personajes.get(restantes.get(i) - 1).getNombre());
        }
        int opt2 = engine.getInput().readIntInRange(1, restantes.size());
        if (opt2 == -1) return;

        atacante = personajes.get(opt1 - 1);
        defensor = personajes.get(restantes.get(opt2 - 1) - 1);
        progAtacante = new CharacterProgression(atacante.getClase());
        turno = 0;

        engine.getRenderer().renderCombatLog("¡" + atacante.getNombre() + " vs " + defensor.getNombre() + "!");
        waitForInput();
        ejecutarCombate();
    }

    private void ejecutarCombate() {
        while (atacante.estaVivo() && defensor.estaVivo() && turno < 20) {
            engine.getRenderer().clear();
            engine.getRenderer().renderTitle(" TURNO " + (turno + 1) + " ");

            engine.getRenderer().renderStatBar(atacante.getNombre(), atacante.getVida(), 100);
            engine.getRenderer().renderStatBar(defensor.getNombre(), defensor.getVida(), 100);
            engine.getRenderer().renderSeparator();

            boolean turnoJugador = turno % 2 == 0;
            Personaje current = turnoJugador ? atacante : defensor;
            Personaje target = turnoJugador ? defensor : atacante;

            if (turnoJugador) {
                engine.getRenderer().renderInfo("", "Tu turno! (" + current.getNombre() + ")");
                engine.getRenderer().renderInfo("", "Atacar normal (1) | Usar habilidad (2): ");
                int choice = engine.getInput().readInt();

                if (choice == 2 && progAtacante.getSkillManager().getHabilidadesDisponibles().size() > 0) {
                    usarHabilidad(current, target);
                } else {
                    ataqueNormal(current, target);
                }
            } else {
                ataqueEnemigo(current, target);
            }

            progAtacante.getSkillManager().reducirCooldowns();
            turno++;
            waitForInput();
        }

        if (!atacante.estaVivo()) {
            engine.getRenderer().renderError("¡" + atacante.getNombre() + " ha sido derrotado!");
        } else if (!defensor.estaVivo()) {
            engine.getRenderer().renderSuccess("¡" + atacante.getNombre() + " ha ganado el combate!");
        } else {
            engine.getRenderer().renderInfo("", "¡El combate terminó en empate tras " + turno + " turnos!");
        }
        waitForInput();
    }

    private void ataqueNormal(Personaje atacante, Personaje defensor) {
        DamageStrategy strategy = atacante.getClase() == ClasePersonaje.MAGO
                ? new MagicDamageStrategy()
                : new PhysicalDamageStrategy();
        int danio = atacante.atacar(defensor, strategy);
        defensor.recibirDanio(danio);
        engine.getRenderer().renderCombatLog(atacante.getNombre() + " ataca a " + defensor.getNombre());
        engine.getRenderer().renderDamage(danio);
    }

    private void usarHabilidad(Personaje current, Personaje target) {
        var habilidades = progAtacante.getSkillManager().getHabilidadesDisponibles();
        engine.getRenderer().renderInfo("", "Habilidades disponibles:");
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad h = habilidades.get(i);
            System.out.printf("  %d. %s (Maná: %d, Daño: %d)%n",
                    i + 1, h.getNombre(), h.getCosteMana(), h.getDanioBase());
        }
        int opt = engine.getInput().readIntInRange(1, habilidades.size());
        if (opt == -1) {
            ataqueNormal(current, target);
            return;
        }

        Habilidad habilidad = habilidades.get(opt - 1);
        if (!progAtacante.getSkillManager().verificarMana(current.getMana(), habilidad.getCosteMana())) {
            engine.getRenderer().renderError("Maná insuficiente!");
            waitForInput();
            ataqueNormal(current, target);
            return;
        }

        Habilidad usada = progAtacante.getSkillManager().usarHabilidad(habilidad.getId());
        current.setMana(current.getMana() - usada.getCosteMana());

        if (usada instanceof HabilidadOfensiva ofensiva) {
            int danio = ofensiva.calcularDanio(current.getAtaque(), 1);
            target.recibirDanio(danio);
            engine.getRenderer().renderCombatLog(current.getNombre() + " usa " + usada.getNombre() + "!");
            engine.getRenderer().renderDamage(danio);
        } else if (usada instanceof HabilidadCurativa curativa) {
            int curacion = curativa.calcularDanio(current.getAtaque(), 1);
            current.setVida(Math.min(100, current.getVida() + curacion));
            engine.getRenderer().renderHealLog(current.getNombre() + " se cura " + curacion + " HP!");
        } else if (usada instanceof HabilidadBuff buff) {
            engine.getRenderer().renderBuffLog(current.getNombre() + " activa " + buff.getNombre() + "!");
        }
    }

    private void ataqueEnemigo(Personaje current, Personaje target) {
        DamageStrategy strategy = new PhysicalDamageStrategy();
        int danio = current.atacar(target, strategy);
        target.recibirDanio(danio);
        engine.getRenderer().renderCombatLog(current.getNombre() + " ataca a " + target.getNombre());
        engine.getRenderer().renderDamage(danio);
    }
}
