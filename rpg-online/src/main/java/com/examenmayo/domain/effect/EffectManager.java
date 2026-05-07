package com.examenmayo.domain.effect;

import com.examenmayo.domain.enums.EstadoEfecto;
import java.util.*;

public class EffectManager {
    private final Map<EstadoEfecto, StatusEffect> efectosActivos = new HashMap<>();

    public void aplicarEfecto(EstadoEfecto tipo, int duracion, int poder) {
        efectosActivos.put(tipo, new StatusEffect(tipo, duracion, poder));
    }

    public void tickEfectos() {
        List<EstadoEfecto> expirados = new ArrayList<>();
        efectosActivos.values().forEach(efecto -> {
            efecto.tick();
            if (!efecto.estaActivo()) {
                expirados.add(efecto.getTipo());
            }
        });
        expirados.forEach(efectosActivos::remove);
    }

    public boolean tieneEfecto(EstadoEfecto tipo) {
        return efectosActivos.containsKey(tipo) && efectosActivos.get(tipo).estaActivo();
    }

    public int calcularDanioExtra() {
        int extra = 0;
        if (tieneEfecto(EstadoEfecto.ENVENENADO)) {
            extra += efectosActivos.get(EstadoEfecto.ENVENENADO).getPoder();
        }
        if (tieneEfecto(EstadoEfecto.QUEMADO)) {
            extra += efectosActivos.get(EstadoEfecto.QUEMADO).getPoder();
        }
        return extra;
    }

    public int getModificadorDefensa() {
        if (tieneEfecto(EstadoEfecto.PROTEGIDO)) {
            return efectosActivos.get(EstadoEfecto.PROTEGIDO).getPoder();
        }
        if (tieneEfecto(EstadoEfecto.DEBILITADO)) {
            return -efectosActivos.get(EstadoEfecto.DEBILITADO).getPoder();
        }
        return 0;
    }

    public int getModificadorAtaque() {
        if (tieneEfecto(EstadoEfecto.FORTALECIDO)) {
            return efectosActivos.get(EstadoEfecto.FORTALECIDO).getPoder();
        }
        return 0;
    }

    public boolean puedeActuar() {
        return !tieneEfecto(EstadoEfecto.PARALIZADO) && !tieneEfecto(EstadoEfecto.DORMIDO);
    }

    public void limpiar() {
        efectosActivos.clear();
    }

    public List<StatusEffect> getEfectosActivos() {
        return new ArrayList<>(efectosActivos.values());
    }
}
