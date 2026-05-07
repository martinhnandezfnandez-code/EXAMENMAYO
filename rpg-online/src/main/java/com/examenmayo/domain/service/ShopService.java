package com.examenmayo.domain.service;

import com.examenmayo.domain.exception.BusinessException;
import com.examenmayo.domain.exception.ResourceNotFoundException;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.ObjetoRepository;
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
public class ShopService {

    private final PlayerService playerService;
    private final CharacterService characterService;
    private final ObjetoRepository objetoRepository;

    public List<Objeto> listarCatalogo() {
        return objetoRepository.findAll();
    }

    @Transactional
    public ShopTransaction comprarObjeto(UUID jugadorId, UUID objetoId) {
        Jugador jugador = playerService.findById(jugadorId);
        Objeto objeto = objetoRepository.findById(objetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Objeto", objetoId));

        validarOro(jugador, objeto);

        Personaje personaje = jugador.getPersonajes().stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("El jugador no tiene personajes"));

        Inventario inventario = personaje.getInventario();
        if (inventario == null) {
            throw new BusinessException("El personaje no tiene inventario");
        }

        jugador.restarOro(BigDecimal.valueOf(objeto.getValor()));
        inventario.agregarObjeto(objeto);

        log.info("Jugador {} compró {} por {} oro", jugador.getNickname(), objeto.getNombre(), objeto.getValor());
        return new ShopTransaction(jugador, objeto, true, "Compra exitosa");
    }

    @Transactional
    public ShopTransaction venderObjeto(UUID jugadorId, UUID objetoId) {
        Jugador jugador = playerService.findById(jugadorId);
        Objeto objeto = objetoRepository.findById(objetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Objeto", objetoId));

        if (objeto.getInventario() == null) {
            throw new BusinessException("El objeto no está en ningún inventario");
        }

        int precioVenta = objeto.getValor() / 2;
        jugador.agregarOro(BigDecimal.valueOf(precioVenta));
        objeto.getInventario().eliminarObjeto(objeto);
        objetoRepository.delete(objeto);

        log.info("Jugador {} vendió {} por {} oro", jugador.getNickname(), objeto.getNombre(), precioVenta);
        return new ShopTransaction(jugador, objeto, true, "Venta exitosa por " + precioVenta + " oro");
    }

    public boolean validarOro(Jugador jugador, Objeto objeto) {
        if (jugador.getOro().compareTo(BigDecimal.valueOf(objeto.getValor())) < 0) {
            throw new BusinessException("Oro insuficiente. Necesitas " + objeto.getValor() + " oro, tienes " + jugador.getOro());
        }
        return true;
    }

    public record ShopTransaction(Jugador jugador, Objeto objeto, boolean exitoso, String mensaje) {}
}
