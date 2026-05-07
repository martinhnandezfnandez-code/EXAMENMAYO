package com.examenmayo.domain.skill;

import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.exception.BusinessException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SkillManager {
    private final Map<UUID, Habilidad> habilidadesDesbloqueadas = new HashMap<>();
    private final List<Habilidad> todasLasHabilidades;

    public SkillManager(ClasePersonaje clase) {
        this.todasLasHabilidades = SkillFactory.crearHabilidadesParaClase(clase);
    }

    public void desbloquearHabilidad(int nivel) {
        todasLasHabilidades.stream()
                .filter(h -> h.getNivelRequerido() == nivel)
                .filter(h -> !habilidadesDesbloqueadas.containsKey(h.getId()))
                .forEach(h -> {
                    habilidadesDesbloqueadas.put(h.getId(), h);
                    log.info("Habilidad desbloqueada: {}", h.getNombre());
                });
    }

    public List<Habilidad> getHabilidadesDisponibles() {
        return habilidadesDesbloqueadas.values().stream()
                .filter(Habilidad::estaDisponible)
                .collect(Collectors.toList());
    }

    public List<Habilidad> getHabilidadesDesbloqueadas() {
        return new ArrayList<>(habilidadesDesbloqueadas.values());
    }

    public boolean verificarMana(int manaActual, int costeMana) {
        return manaActual >= costeMana;
    }

    public Habilidad usarHabilidad(UUID habilidadId) {
        Habilidad habilidad = habilidadesDesbloqueadas.get(habilidadId);
        if (habilidad == null) {
            throw new BusinessException("Habilidad no desbloqueada");
        }
        if (!habilidad.estaDisponible()) {
            throw new BusinessException("Habilidad en cooldown");
        }
        habilidad.usar();
        log.info("Habilidad usada: {}", habilidad.getNombre());
        return habilidad;
    }

    public void reducirCooldowns() {
        habilidadesDesbloqueadas.values().forEach(Habilidad::reducirCooldown);
    }

    public void desbloquearTodas() {
        todasLasHabilidades.forEach(h -> habilidadesDesbloqueadas.put(h.getId(), h));
    }
}
