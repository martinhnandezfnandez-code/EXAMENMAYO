package com.examenmayo.application.mapper;

import com.examenmayo.application.dto.ClanResponse;
import com.examenmayo.application.dto.CombateResponse;
import com.examenmayo.application.dto.InventarioResponse;
import com.examenmayo.application.dto.JugadorResponse;
import com.examenmayo.application.dto.MisionResponse;
import com.examenmayo.application.dto.ObjetoResponse;
import com.examenmayo.application.dto.PersonajeResponse;
import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.enums.EstadoMision;
import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.ResultadoCombate;
import com.examenmayo.domain.enums.TipoEfecto;
import com.examenmayo.domain.enums.TipoObjeto;
import com.examenmayo.domain.model.Clan;
import com.examenmayo.domain.model.Combate;
import com.examenmayo.domain.model.Inventario;
import com.examenmayo.domain.model.Jugador;
import com.examenmayo.domain.model.Mision;
import com.examenmayo.domain.model.Objeto;
import com.examenmayo.domain.model.Personaje;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T20:48:30+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class RpgMapperImpl implements RpgMapper {

    @Override
    public JugadorResponse toJugadorResponse(Jugador jugador) {
        if ( jugador == null ) {
            return null;
        }

        UUID clanId = null;
        String clanNombre = null;
        List<PersonajeResponse> personajes = null;
        UUID id = null;
        String nickname = null;
        int nivel = 0;
        long experiencia = 0L;
        BigDecimal oro = null;

        clanId = jugadorClanId( jugador );
        clanNombre = jugadorClanNombre( jugador );
        personajes = personajeListToPersonajeResponseList( jugador.getPersonajes() );
        id = jugador.getId();
        nickname = jugador.getNickname();
        nivel = jugador.getNivel();
        experiencia = jugador.getExperiencia();
        oro = jugador.getOro();

        JugadorResponse jugadorResponse = new JugadorResponse( id, nickname, nivel, experiencia, oro, clanId, clanNombre, personajes );

        return jugadorResponse;
    }

    @Override
    public PersonajeResponse toPersonajeResponse(Personaje personaje) {
        if ( personaje == null ) {
            return null;
        }

        UUID jugadorId = null;
        UUID id = null;
        String nombre = null;
        ClasePersonaje clase = null;
        int vida = 0;
        int mana = 0;
        int ataque = 0;
        int defensa = 0;
        InventarioResponse inventario = null;

        jugadorId = personajeJugadorId( personaje );
        id = personaje.getId();
        nombre = personaje.getNombre();
        clase = personaje.getClase();
        vida = personaje.getVida();
        mana = personaje.getMana();
        ataque = personaje.getAtaque();
        defensa = personaje.getDefensa();
        inventario = toInventarioResponse( personaje.getInventario() );

        int poderTotal = personaje.calcularPoderTotal();

        PersonajeResponse personajeResponse = new PersonajeResponse( id, nombre, clase, vida, mana, ataque, defensa, poderTotal, jugadorId, inventario );

        return personajeResponse;
    }

    @Override
    public InventarioResponse toInventarioResponse(Inventario inventario) {
        if ( inventario == null ) {
            return null;
        }

        UUID id = null;
        int capacidadMaxima = 0;
        List<ObjetoResponse> objetos = null;

        id = inventario.getId();
        capacidadMaxima = inventario.getCapacidadMaxima();
        objetos = objetoListToObjetoResponseList( inventario.getObjetos() );

        int objetosUsados = inventario.listarObjetos().size();

        InventarioResponse inventarioResponse = new InventarioResponse( id, capacidadMaxima, objetosUsados, objetos );

        return inventarioResponse;
    }

    @Override
    public ObjetoResponse toObjetoResponse(Objeto objeto) {
        if ( objeto == null ) {
            return null;
        }

        UUID inventarioId = null;
        UUID id = null;
        String nombre = null;
        TipoObjeto tipo = null;
        Rareza rareza = null;
        int valor = 0;

        inventarioId = objetoInventarioId( objeto );
        id = objeto.getId();
        nombre = objeto.getNombre();
        tipo = objeto.getTipo();
        rareza = objeto.getRareza();
        valor = objeto.getValor();

        Integer danio = null;
        Integer defensaExtra = null;
        TipoEfecto efecto = null;
        Integer potencia = null;

        ObjetoResponse objetoResponse = new ObjetoResponse( id, nombre, tipo, rareza, valor, danio, defensaExtra, efecto, potencia, inventarioId );

        return objetoResponse;
    }

    @Override
    public ClanResponse toClanResponse(Clan clan) {
        if ( clan == null ) {
            return null;
        }

        UUID liderId = null;
        String liderNickname = null;
        UUID id = null;
        String nombre = null;
        int ranking = 0;

        liderId = clanLiderId( clan );
        liderNickname = clanLiderNickname( clan );
        id = clan.getId();
        nombre = clan.getNombre();
        ranking = clan.getRanking();

        int numeroMiembros = clan.getMiembros().size();

        ClanResponse clanResponse = new ClanResponse( id, nombre, liderId, liderNickname, numeroMiembros, ranking );

        return clanResponse;
    }

    @Override
    public CombateResponse toCombateResponse(Combate combate) {
        if ( combate == null ) {
            return null;
        }

        UUID id = null;
        int turnos = 0;
        ResultadoCombate resultado = null;

        id = combate.getId();
        turnos = combate.getTurnos();
        resultado = combate.getResultado();

        List<UUID> participantesId = combate.getParticipantes().stream().map(p -> p.getId()).toList();
        UUID ganadorId = combate.determinarGanador() != null ? combate.determinarGanador().getId() : null;

        CombateResponse combateResponse = new CombateResponse( id, participantesId, turnos, resultado, ganadorId );

        return combateResponse;
    }

    @Override
    public MisionResponse toMisionResponse(Mision mision) {
        if ( mision == null ) {
            return null;
        }

        UUID id = null;
        String nombre = null;
        String descripcion = null;
        int recompensa = 0;
        EstadoMision estado = null;

        id = mision.getId();
        nombre = mision.getNombre();
        descripcion = mision.getDescripcion();
        recompensa = mision.getRecompensa();
        estado = mision.getEstado();

        List<UUID> jugadoresId = mision.getJugadores().stream().map(j -> j.getId()).toList();

        MisionResponse misionResponse = new MisionResponse( id, nombre, descripcion, recompensa, estado, jugadoresId );

        return misionResponse;
    }

    private UUID jugadorClanId(Jugador jugador) {
        if ( jugador == null ) {
            return null;
        }
        Clan clan = jugador.getClan();
        if ( clan == null ) {
            return null;
        }
        UUID id = clan.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String jugadorClanNombre(Jugador jugador) {
        if ( jugador == null ) {
            return null;
        }
        Clan clan = jugador.getClan();
        if ( clan == null ) {
            return null;
        }
        String nombre = clan.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    protected List<PersonajeResponse> personajeListToPersonajeResponseList(List<Personaje> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonajeResponse> list1 = new ArrayList<PersonajeResponse>( list.size() );
        for ( Personaje personaje : list ) {
            list1.add( toPersonajeResponse( personaje ) );
        }

        return list1;
    }

    private UUID personajeJugadorId(Personaje personaje) {
        if ( personaje == null ) {
            return null;
        }
        Jugador jugador = personaje.getJugador();
        if ( jugador == null ) {
            return null;
        }
        UUID id = jugador.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<ObjetoResponse> objetoListToObjetoResponseList(List<Objeto> list) {
        if ( list == null ) {
            return null;
        }

        List<ObjetoResponse> list1 = new ArrayList<ObjetoResponse>( list.size() );
        for ( Objeto objeto : list ) {
            list1.add( toObjetoResponse( objeto ) );
        }

        return list1;
    }

    private UUID objetoInventarioId(Objeto objeto) {
        if ( objeto == null ) {
            return null;
        }
        Inventario inventario = objeto.getInventario();
        if ( inventario == null ) {
            return null;
        }
        UUID id = inventario.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID clanLiderId(Clan clan) {
        if ( clan == null ) {
            return null;
        }
        Jugador lider = clan.getLider();
        if ( lider == null ) {
            return null;
        }
        UUID id = lider.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String clanLiderNickname(Clan clan) {
        if ( clan == null ) {
            return null;
        }
        Jugador lider = clan.getLider();
        if ( lider == null ) {
            return null;
        }
        String nickname = lider.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }
}
