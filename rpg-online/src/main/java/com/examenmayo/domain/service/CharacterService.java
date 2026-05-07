package com.examenmayo.domain.service;

import com.examenmayo.domain.exception.BusinessException;
import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final PersonajeRepository personajeRepository;
    private final JugadorRepository jugadorRepository;
    private final InventarioRepository inventarioRepository;

    public List<Personaje> findAll() {
        return personajeRepository.findAll();
    }

    public Personaje findById(UUID id) {
        return personajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje", id));
    }

    @Transactional
    public Personaje create(UUID jugadorId, Personaje personaje) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador", jugadorId));
        personaje.setJugador(jugador);
        Inventario inventario = new Inventario();
        inventario.setPersonaje(personaje);
        personaje.setInventario(inventario);
        log.info("Creando personaje: {} para jugador: {}", personaje.getNombre(), jugador.getNickname());
        return personajeRepository.save(personaje);
    }

    @Transactional
    public void delete(UUID id) {
        Personaje personaje = findById(id);
        personajeRepository.delete(personaje);
        log.info("Eliminando personaje: {}", id);
    }

    @Transactional
    public Personaje usarObjeto(UUID personajeId, UUID objetoId) {
        Personaje personaje = findById(personajeId);
        Inventario inventario = personaje.getInventario();
        Objeto objeto = inventario.listarObjetos().stream()
                .filter(o -> o.getId().equals(objetoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Objeto", objetoId));
        if (!(objeto instanceof Consumible consumible)) {
            throw new BusinessException("Solo se pueden usar objetos consumibles");
        }
        personaje.usarObjeto(consumible);
        log.info("Personaje {} usó {}", personaje.getNombre(), consumible.getNombre());
        return personajeRepository.save(personaje);
    }
}
