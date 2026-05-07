package com.examenmayo.application.mapper;

import com.examenmayo.application.dto.*;
import com.examenmayo.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RpgMapper {

    @Mapping(target = "clanId", source = "clan.id")
    @Mapping(target = "clanNombre", source = "clan.nombre")
    @Mapping(target = "personajes", source = "personajes")
    JugadorResponse toJugadorResponse(Jugador jugador);

    @Mapping(target = "poderTotal", expression = "java(personaje.calcularPoderTotal())")
    @Mapping(target = "jugadorId", source = "jugador.id")
    PersonajeResponse toPersonajeResponse(Personaje personaje);

    @Mapping(target = "objetosUsados", expression = "java(inventario.listarObjetos().size())")
    InventarioResponse toInventarioResponse(Inventario inventario);

    @Mapping(target = "inventarioId", source = "inventario.id")
    ObjetoResponse toObjetoResponse(Objeto objeto);

    @Mapping(target = "liderId", source = "lider.id")
    @Mapping(target = "liderNickname", source = "lider.nickname")
    @Mapping(target = "numeroMiembros", expression = "java(clan.getMiembros().size())")
    ClanResponse toClanResponse(Clan clan);

    @Mapping(target = "participantesId", expression = "java(combate.getParticipantes().stream().map(p -> p.getId()).toList())")
    @Mapping(target = "ganadorId", expression = "java(combate.determinarGanador() != null ? combate.determinarGanador().getId() : null)")
    CombateResponse toCombateResponse(Combate combate);

    @Mapping(target = "jugadoresId", expression = "java(mision.getJugadores().stream().map(j -> j.getId()).toList())")
    MisionResponse toMisionResponse(Mision mision);
}
