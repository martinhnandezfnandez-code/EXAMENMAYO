package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import lombok.Getter;

@Getter
public class DataStore {
    private final JsonDatabase database;
    private final String STORE_PLAYERS = "players";
    private final String STORE_CHARACTERS = "characters";
    private final String STORE_CLANS = "clans";
    private final String STORE_MISSIONS = "missions";
    private final String STORE_ITEMS = "items";

    private final PlayerJsonRepository players;
    private final CharacterJsonRepository characters;
    private final ClanJsonRepository clans;
    private final MissionJsonRepository missions;
    private final ItemJsonRepository items;

    public DataStore() {
        this.database = new JsonDatabase();
        this.players = new PlayerJsonRepository(database, STORE_PLAYERS);
        this.characters = new CharacterJsonRepository(database, STORE_CHARACTERS);
        this.clans = new ClanJsonRepository(database, STORE_CLANS);
        this.missions = new MissionJsonRepository(database, STORE_MISSIONS);
        this.items = new ItemJsonRepository(database, STORE_ITEMS);
        loadAll();
    }

    private void loadAll() {
        database.loadAll(STORE_PLAYERS, Jugador.class);
        database.loadAll(STORE_CHARACTERS, Personaje.class);
        database.loadAll(STORE_CLANS, Clan.class);
        database.loadAll(STORE_MISSIONS, Mision.class);
        database.loadAll(STORE_ITEMS, Objeto.class);
    }
}
