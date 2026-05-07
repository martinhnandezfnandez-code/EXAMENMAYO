package com.examenmayo.domain.service;

import com.examenmayo.domain.exception.BusinessException;
import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.ClanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanRepository clanRepository;
    private final PlayerService playerService;

    public List<Clan> findAll() {
        return clanRepository.findAll();
    }

    public Clan findById(UUID id) {
        return clanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clan", id));
    }

    @Transactional
    public Clan create(Clan clan, UUID liderId) {
        if (clanRepository.existsByNombre(clan.getNombre())) {
            throw new BusinessException("El nombre del clan ya existe");
        }
        Jugador lider = playerService.findById(liderId);
        clan.setLider(lider);
        clan.getMiembros().add(lider);
        lider.setClan(clan);
        log.info("Creando clan: {} con líder: {}", clan.getNombre(), lider.getNickname());
        return clanRepository.save(clan);
    }

    @Transactional
    public Clan agregarMiembro(UUID clanId, UUID jugadorId) {
        Clan clan = findById(clanId);
        Jugador jugador = playerService.findById(jugadorId);
        clan.agregarMiembro(jugador);
        clan.calcularRanking();
        log.info("Jugador {} se unió al clan {}", jugador.getNickname(), clan.getNombre());
        return clanRepository.save(clan);
    }

    @Transactional
    public Clan expulsarMiembro(UUID clanId, UUID jugadorId) {
        Clan clan = findById(clanId);
        Jugador jugador = playerService.findById(jugadorId);
        clan.expulsarMiembro(jugador);
        clan.calcularRanking();
        log.info("Jugador {} expulsado del clan {}", jugador.getNickname(), clan.getNombre());
        return clanRepository.save(clan);
    }

    @Transactional
    public void delete(UUID id) {
        Clan clan = findById(id);
        clanRepository.delete(clan);
        log.info("Eliminando clan: {}", id);
    }
}
