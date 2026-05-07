package com.examenmayo.infrastructure.configuration;

import com.examenmayo.domain.enums.*;
import com.examenmayo.domain.model.*;
import com.examenmayo.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final JugadorRepository jugadorRepository;
    private final PersonajeRepository personajeRepository;
    private final ClanRepository clanRepository;
    private final MisionRepository misionRepository;
    private final ObjetoRepository objetoRepository;

    @Override
    public void run(String... args) {
        if (jugadorRepository.count() > 0) {
            log.info("Base de datos ya inicializada");
            return;
        }

        log.info("Inicializando datos de demostración...");

        Jugador heroe = Jugador.builder()
                .nickname("HeroeLegendario")
                .nivel(10)
                .experiencia(500)
                .oro(BigDecimal.valueOf(1000))
                .build();
        heroe = jugadorRepository.save(heroe);

        Jugador mago = Jugador.builder()
                .nickname("MagoSupremo")
                .nivel(5)
                .experiencia(200)
                .oro(BigDecimal.valueOf(500))
                .build();
        mago = jugadorRepository.save(mago);

        Personaje guerrero = Personaje.builder()
                .nombre("Thorn")
                .clase(ClasePersonaje.GUERRERO)
                .vida(120)
                .mana(30)
                .ataque(25)
                .defensa(15)
                .jugador(heroe)
                .build();
        Inventario invGuerrero = new Inventario();
        invGuerrero.setPersonaje(guerrero);
        guerrero.setInventario(invGuerrero);
        guerrero = personajeRepository.save(guerrero);

        Arma espada = Arma.builder()
                .nombre("Espada Legendaria")
                .rareza(Rareza.LEGENDARIO)
                .valor(500)
                .danio(30)
                .build();
        espada.setInventario(invGuerrero);
        espada = (Arma) objetoRepository.save(espada);
        invGuerrero.getObjetos().add(espada);

        Armadura armadura = Armadura.builder()
                .nombre("Armadura de Dragon")
                .rareza(Rareza.EPICO)
                .valor(300)
                .defensaExtra(20)
                .build();
        armadura.setInventario(invGuerrero);
        armadura = (Armadura) objetoRepository.save(armadura);
        invGuerrero.getObjetos().add(armadura);

        Consumible pocion = Consumible.builder()
                .nombre("Pocion de Vida")
                .rareza(Rareza.COMUN)
                .valor(50)
                .efecto(TipoEfecto.CURACION)
                .potencia(50)
                .build();
        pocion.setInventario(invGuerrero);
        pocion = (Consumible) objetoRepository.save(pocion);
        invGuerrero.getObjetos().add(pocion);

        Personaje arquero = Personaje.builder()
                .nombre("Swift")
                .clase(ClasePersonaje.ARQUERO)
                .vida(80)
                .mana(60)
                .ataque(20)
                .defensa(8)
                .jugador(mago)
                .build();
        Inventario invArquero = new Inventario();
        invArquero.setPersonaje(arquero);
        arquero.setInventario(invArquero);
        arquero = personajeRepository.save(arquero);

        Clan alianza = Clan.builder()
                .nombre("Alianza Dorada")
                .lider(heroe)
                .build();
        alianza.getMiembros().add(heroe);
        heroe.setClan(alianza);
        alianza = clanRepository.save(alianza);

        Mision mision1 = Mision.builder()
                .nombre("Derrotar al Dragon")
                .descripcion("Enfrentate al dragon de fuego en las montañas")
                .recompensa(1000)
                .estado(EstadoMision.PENDIENTE)
                .build();
        misionRepository.save(mision1);

        Mision mision2 = Mision.builder()
                .nombre("Recolectar Hierbas")
                .descripcion("Encuentra 10 hierbas magicas en el bosque")
                .recompensa(200)
                .estado(EstadoMision.PENDIENTE)
                .build();
        misionRepository.save(mision2);

        log.info("Datos de demostración inicializados correctamente");
    }
}
