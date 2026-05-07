package com.examenmayo.domain.service;

import com.examenmayo.domain.exception.BusinessException;
import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final JugadorRepository jugadorRepository;
    private final PersonajeRepository personajeRepository;

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Jugador findById(UUID id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador", id));
    }

    public Jugador findByIdWithPersonajes(UUID id) {
        return jugadorRepository.findByIdWithPersonajes(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador", id));
    }

    @Transactional
    public Jugador create(Jugador jugador) {
        if (jugadorRepository.existsByNickname(jugador.getNickname())) {
            throw new BusinessException("El nickname ya está en uso");
        }
        log.info("Creando jugador: {}", jugador.getNickname());
        return jugadorRepository.save(jugador);
    }

    @Transactional
    public Jugador update(UUID id, Jugador update) {
        Jugador existing = findById(id);
        existing.setNickname(update.getNickname());
        log.info("Actualizando jugador: {}", id);
        return jugadorRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Jugador jugador = findById(id);
        jugadorRepository.delete(jugador);
        log.info("Eliminando jugador: {}", id);
    }

    @Transactional
    public Jugador subirNivel(UUID id) {
        Jugador jugador = findById(id);
        jugador.subirNivel();
        log.info("Jugador {} subió al nivel {}", jugador.getNickname(), jugador.getNivel());
        return jugadorRepository.save(jugador);
    }

    public List<Personaje> getPersonajes(UUID jugadorId) {
        return personajeRepository.findByJugadorId(jugadorId);
    }
}
