package com.examenmayo.domain.service;

import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.CombateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombatService {

    private final CombateRepository combateRepository;
    private final CharacterService characterService;

    public List<Combate> findAll() {
        return combateRepository.findAll();
    }

    public Combate findById(UUID id) {
        return combateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combate", id));
    }

    @Transactional
    public Combate iniciarCombate(UUID atacanteId, UUID defensorId, String estrategia) {
        Personaje atacante = characterService.findById(atacanteId);
        Personaje defensor = characterService.findById(defensorId);

        Combate combate = Combate.builder()
                .participantes(List.of(atacante, defensor))
                .build();

        DamageStrategy strategy = switch (estrategia != null ? estrategia.toLowerCase() : "physical") {
            case "magic" -> new MagicDamageStrategy();
            case "true" -> new TrueDamageStrategy();
            default -> new PhysicalDamageStrategy();
        };
        combate.setStrategy(strategy);
        combate.iniciarCombate();

        int maxTurnos = 20;
        for (int i = 0; i < maxTurnos; i++) {
            combate.ejecutarTurno();
            if (combate.getResultado() != com.examenmayo.domain.enums.ResultadoCombate.EMPATE) {
                break;
            }
        }

        log.info("Combate {} finalizado: {} ({} turnos)", combate.getId(), combate.getResultado(), combate.getTurnos());
        return combateRepository.save(combate);
    }
}
