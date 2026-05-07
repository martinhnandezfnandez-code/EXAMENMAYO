package com.examenmayo.domain.service;

import com.examenmayo.domain.enums.EstadoMision;
import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.Jugador;
import com.examenmayo.domain.model.Mision;
import com.examenmayo.domain.repository.MisionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MisionService {

    private final MisionRepository misionRepository;
    private final PlayerService playerService;

    public List<Mision> findAll() {
        return misionRepository.findAll();
    }

    public Mision findById(UUID id) {
        return misionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mision", id));
    }

    @Transactional
    public Mision create(Mision mision) {
        log.info("Creando misión: {}", mision.getNombre());
        return misionRepository.save(mision);
    }

    @Transactional
    public Mision asignarJugador(UUID misionId, UUID jugadorId) {
        Mision mision = findById(misionId);
        Jugador jugador = playerService.findById(jugadorId);
        mision.asignarJugador(jugador);
        log.info("Misión {} asignada a jugador {}", mision.getNombre(), jugador.getNickname());
        return misionRepository.save(mision);
    }

    @Transactional
    public Mision completar(UUID misionId) {
        Mision mision = findById(misionId);
        mision.completar();
        log.info("Misión {} completada", mision.getNombre());
        return misionRepository.save(mision);
    }

    public List<Mision> findByEstado(EstadoMision estado) {
        return misionRepository.findByEstado(estado);
    }

    public List<Mision> findByJugador(UUID jugadorId) {
        return misionRepository.findByJugadoresId(jugadorId);
    }

    @Transactional
    public void delete(UUID id) {
        Mision mision = findById(id);
        misionRepository.delete(mision);
    }
}
